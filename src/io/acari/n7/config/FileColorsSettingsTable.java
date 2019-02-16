package io.acari.n7.config;

import com.intellij.ui.FileColorManager;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.ColorIcon;
import com.intellij.util.ui.EditableModel;
import com.intellij.util.ui.EmptyIcon;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public abstract class FileColorSettingsTable extends JBTable {
  private static final int NAME_COLUMN = 0;
  private static final int COLOR_COLUMN = 1;

  private final List<FileColorConfiguration> myOriginal;

  public FileColorSettingsTable(@NotNull final List<FileColorConfiguration> configurations) {
    super(new FileColorSettingsTable.ModelAdapter(manager, copy(configurations)));
    setStriped(true);
    myOriginal = configurations;

    setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);

    final TableColumnModel columnModel = getColumnModel();
    final TableColumn colorColumn = columnModel.getColumn(COLOR_COLUMN);
    colorColumn.setCellRenderer(new FileColorSettingsTable.ColorCellRenderer());
  }

  private static List<FileColorConfiguration> copy(@NotNull final List<FileColorConfiguration> configurations) {
    final List<FileColorConfiguration> result = new ArrayList<>();
    for (FileColorConfiguration c : configurations) {
      try {
        result.add(c.clone());
      }
      catch (CloneNotSupportedException e) {
        assert false : "Should not happen!";
      }
    }

    return result;
  }

  protected abstract void apply(@NotNull final List<FileColorConfiguration> configurations);

  @Override
  public FileColorSettingsTable.ModelAdapter getModel() {
    return (FileColorSettingsTable.ModelAdapter) super.getModel();
  }

  @Override
  public boolean editCellAt(int row, int column, EventObject e) {
    if (e == null || (e instanceof MouseEvent && ((MouseEvent)e).getClickCount() == 1)) return false;
    final Object at = getModel().getValueAt(row, column);
    if (!(at instanceof FileColorConfiguration)) return false;
    final FileColorConfigurationEditDialog dialog = new FileColorConfigurationEditDialog(((FileColorConfiguration)at));
    dialog.getScopeComboBox().setEnabled(false);
    dialog.show();
    return false;
  }

  public boolean isModified() {
    final List<FileColorConfiguration> current = getModel().getConfigurations();

    if (myOriginal.size() != current.size()) {
      return true;
    }

    for (int i = 0; i < current.size(); i++) {
      if (!myOriginal.get(i).equals(current.get(i))) {
        return true;
      }
    }

    return false;
  }

  public void reset() {
    getModel().setConfigurations(myOriginal);
  }

  public void performRemove() {
    final int rowCount = getSelectedRowCount();
    if (rowCount > 0) {
      final int[] rows = getSelectedRows();
      for (int i = rows.length - 1; i >= 0; i--) {
        removeConfiguration(rows[i]);
      }
    }
  }

  public void moveUp() {
    final int rowCount = getSelectedRowCount();
    if (rowCount == 1) {
      final int index = getModel().moveUp(getSelectedRows()[0]);
      if (index > -1) {
        getSelectionModel().setSelectionInterval(index, index);
      }
    }
  }

  public void moveDown() {
    final int rowCount = getSelectedRowCount();
    if (rowCount == 1) {
      final int index = getModel().moveDown(getSelectedRows()[0]);
      if (index > -1) {
        getSelectionModel().setSelectionInterval(index, index);
      }
    }
  }

  public void apply() {
    if (isModified()) {
      apply(getModel().getConfigurations());
    }
  }

  public FileColorConfiguration removeConfiguration(final int index) {
    final FileColorConfiguration removed = getModel().remove(index);

    final int rowCount = getRowCount();
    if (rowCount > 0) {
      if (index > rowCount - 1) {
        getSelectionModel().setSelectionInterval(rowCount - 1, rowCount - 1);
      } else {
        getSelectionModel().setSelectionInterval(index, index);
      }
    }

    return removed;
  }

  public void addConfiguration(@NotNull final FileColorConfiguration configuration) {
    getModel().add(configuration);
  }

  private static class ModelAdapter extends AbstractTableModel implements EditableModel {
    private final FileColorManager myManager;
    private List<FileColorConfiguration> myConfigurations;

    private ModelAdapter(FileColorManager manager, final List<FileColorConfiguration> configurations) {
      myManager = manager;
      myConfigurations = configurations;
    }

    @Override
    public String getColumnName(int column) {
      return column == NAME_COLUMN ? "Scope" : "Color";
    }

    @Override
    public int getRowCount() {
      return myConfigurations.size();
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      return 0 <= rowIndex && rowIndex < myConfigurations.size()
          ? myConfigurations.get(rowIndex)
          : null;
    }

    @NotNull
    public List<FileColorConfiguration> getConfigurations() {
      return myConfigurations;
    }

    public FileColorConfiguration remove(int index) {
      final FileColorConfiguration removed = myConfigurations.remove(index);
      fireTableRowsDeleted(index, index);
      return removed;
    }

    public void add(@NotNull final FileColorConfiguration configuration) {
      myConfigurations.add(configuration);
      fireTableRowsInserted(myConfigurations.size() - 1, myConfigurations.size() - 1);
    }

    public void setConfigurations(List<FileColorConfiguration> original) {
      myConfigurations = copy(original);
      fireTableDataChanged();
    }

    public int moveUp(int index) {
      if (index > 0) {
        final FileColorConfiguration configuration = myConfigurations.get(index);
        myConfigurations.remove(index);
        myConfigurations.add(index - 1, configuration);
        fireTableRowsUpdated(index - 1, index);
        return index - 1;
      }

      return -1;
    }

    public int moveDown(int index) {
      if (index < getRowCount() - 1) {
        final FileColorConfiguration configuration = myConfigurations.get(index);
        myConfigurations.remove(index);
        myConfigurations.add(index + 1, configuration);
        fireTableRowsUpdated(index, index + 1);
        return index + 1;
      }

      return -1;
    }

    @Override
    public void addRow() {
      final FileColorConfigurationEditDialog dialog = new FileColorConfigurationEditDialog(null);
      dialog.show();

      if (dialog.getExitCode() == 0) {
        myConfigurations.add(dialog.getConfiguration());
        int i = myConfigurations.size() - 1;
        fireTableRowsInserted(i, i);
      }
    }

    @Override
    public void removeRow(int index) {
      myConfigurations.remove(index);
      fireTableRowsDeleted(index, index);
    }

    @Override
    public void exchangeRows(int oldIndex, int newIndex) {
      myConfigurations.add(newIndex, myConfigurations.remove(oldIndex));
      fireTableRowsUpdated(Math.min(oldIndex, newIndex), Math.max(oldIndex, newIndex));
    }

    @Override
    public boolean canExchangeRows(int oldIndex, int newIndex) {
      return true;
    }
  }

  private static class ColorCellRenderer  extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      return super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
    }

    @Override
    protected void setValue(Object value) {
      Icon icon = null;
      String text = null;
      if (value instanceof FileColorConfiguration) {
        icon = getIcon((FileColorConfiguration)value);
        text = getText((FileColorConfiguration)value);
      }
      setIcon(icon);
      setText(text == null ? "" : FileColorManagerImpl.getAlias(text));
    }

    Icon getIcon(FileColorConfiguration configuration) {
      Color color = myManager.getColor(configuration.getColorName());
      return color == null ? EmptyIcon.ICON_16 : JBUI.scale(new ColorIcon(16, 13, color, true));
    }

    String getText(FileColorConfiguration configuration) {
      return configuration.getColorPresentableName();
    }

  }
}
