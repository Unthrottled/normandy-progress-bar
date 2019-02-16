package io.acari.n7.config;

import com.intellij.icons.AllIcons;
import com.intellij.ide.ui.UISettings;
import com.intellij.ide.util.scopeChooser.EditScopesDialog;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author spleaner
 * @author Konstantin Bulenkov
 */
public class FileColorsConfigurablePanel extends JPanel implements Disposable {
  private final FileColorSettingsTable myLocalTable;

  public FileColorsConfigurablePanel() {
    setLayout(new BorderLayout());

    final JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

    add(topPanel, BorderLayout.NORTH);

    final JPanel mainPanel = new JPanel(new GridLayout(2, 1));
    mainPanel.setPreferredSize(JBUI.size(300, 500));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

    final List<FileColorConfiguration> localConfigurations = manager.getApplicationLevelConfigurations();
    myLocalTable = new FileColorSettingsTable(manager, localConfigurations) {
      @Override
      protected void apply(@NotNull List<FileColorConfiguration> configurations) {
        final List<FileColorConfiguration> copied = new ArrayList<>();
        try {
          for (final FileColorConfiguration configuration : configurations) {
            copied.add(configuration.clone());
          }
        } catch (CloneNotSupportedException e) {//
        }
        manager.getModel().setConfigurations(copied, false);
      }
    };

    final JPanel panel = ToolbarDecorator.createDecorator(myLocalTable)
        .addExtraAction(new AnActionButton("Share", AllIcons.Actions.Share) {
          @Override
          public void actionPerformed(@NotNull AnActionEvent e) {
            share();
          }

          @Override
          public boolean isEnabled() {
            return super.isEnabled() && myLocalTable.getSelectedRow() != -1;
          }
        })
        .createPanel();
    final JPanel localPanel = new JPanel(new BorderLayout());
    localPanel.setBorder(IdeBorderFactory.createTitledBorder("Local colors", false));
    localPanel.add(panel, BorderLayout.CENTER);
    mainPanel.add(localPanel);


    myLocalTable.getEmptyText().setText("No local colors");
  }

  @Override
  public void dispose() {
  }

  public boolean isModified() {
    return myLocalTable.isModified();
  }

  public void apply() {
    myLocalTable.apply();
    UISettings.getInstance().fireUISettingsChanged();
  }

  public void reset() {
    if (myLocalTable.isModified()) myLocalTable.reset();
  }
}
