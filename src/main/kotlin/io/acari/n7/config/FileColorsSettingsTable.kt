package io.acari.n7.config

import com.intellij.ui.ColorUtil
import com.intellij.ui.FileColorManager
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.ColorIcon
import com.intellij.util.ui.EditableModel
import com.intellij.util.ui.EmptyIcon
import com.intellij.util.ui.JBUI

import javax.swing.*
import javax.swing.table.AbstractTableModel
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableColumn
import javax.swing.table.TableColumnModel
import java.awt.*
import java.awt.event.MouseEvent
import java.util.ArrayList
import java.util.EventObject


data class JetWashColorConfiguration(val colorValue: String, val colorName: String)

abstract class FileColorSettingsTable(private val myOriginal: MutableList<JetWashColorConfiguration>) :
    JBTable(FileColorSettingsTable.ModelAdapter(copy(myOriginal))) {

  val isModified: Boolean
    get() {
      val current = model.configurations

      if (myOriginal.size != current.size) {
        return true
      }

      for (i in current.indices) {
        if (!myOriginal[i].equals(current[i])) {
          return true
        }
      }

      return false
    }

  init {
    isStriped = true

    setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN)

    val columnModel = getColumnModel()
    val colorColumn = columnModel.getColumn(COLOR_COLUMN)
    colorColumn.cellRenderer = FileColorSettingsTable.ColorCellRenderer()
  }

  protected abstract fun apply(configurations: List<JetWashColorConfiguration>)

  override fun getModel(): FileColorSettingsTable.ModelAdapter {
    return super.getModel() as FileColorSettingsTable.ModelAdapter
  }

  override fun editCellAt(row: Int, column: Int, e: EventObject): Boolean {
    if (e == null || e is MouseEvent && e.clickCount == 1) return false
    val at = model.getValueAt(row, column) as? JetWashColorConfiguration ?: return false
    val dialog = JetWashColorConfigurationEditDialog(at as JetWashColorConfiguration)
    dialog.scopeComboBox?.isEnabled = false
    dialog.show()
    return false
  }

  fun reset() {
    model.configurations = myOriginal
  }

  fun performRemove() {
    val rowCount = selectedRowCount
    if (rowCount > 0) {
      val rows = selectedRows
      for (i in rows.indices.reversed()) {
        removeConfiguration(rows[i])
      }
    }
  }

  fun moveUp() {
    val rowCount = selectedRowCount
    if (rowCount == 1) {
      val index = model.moveUp(selectedRows[0])
      if (index > -1) {
        getSelectionModel().setSelectionInterval(index, index)
      }
    }
  }

  fun moveDown() {
    val rowCount = selectedRowCount
    if (rowCount == 1) {
      val index = model.moveDown(selectedRows[0])
      if (index > -1) {
        getSelectionModel().setSelectionInterval(index, index)
      }
    }
  }

  fun apply() {
    if (isModified) {
      apply(model.configurations)
    }
  }

  fun removeConfiguration(index: Int): JetWashColorConfiguration {
    val removed = model.remove(index)

    val rowCount = rowCount
    if (rowCount > 0) {
      if (index > rowCount - 1) {
        getSelectionModel().setSelectionInterval(rowCount - 1, rowCount - 1)
      } else {
        getSelectionModel().setSelectionInterval(index, index)
      }
    }

    return removed
  }

  fun addConfiguration(configuration: JetWashColorConfiguration) {
    model.add(configuration)
  }

  class ModelAdapter constructor(private var myConfigurations: MutableList<JetWashColorConfiguration>) : AbstractTableModel(), EditableModel {

    var configurations: MutableList<JetWashColorConfiguration>
      get() = myConfigurations
      set(original) {
        myConfigurations = copy(original)
        fireTableDataChanged()
      }

    override fun getColumnName(column: Int): String {
      return if (column == NAME_COLUMN) "Scope" else "Color"
    }

    override fun getRowCount(): Int {
      return myConfigurations!!.size
    }

    override fun getColumnCount(): Int {
      return 2
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
      return if (0 <= rowIndex && rowIndex < myConfigurations!!.size)
        myConfigurations!![rowIndex]
      else
        null
    }

    fun remove(index: Int): JetWashColorConfiguration {
      val removed = myConfigurations!!.removeAt(index)
      fireTableRowsDeleted(index, index)
      return removed
    }

    fun add(configuration: JetWashColorConfiguration) {
      myConfigurations!!.add(configuration)
      fireTableRowsInserted(myConfigurations!!.size - 1, myConfigurations!!.size - 1)
    }

    fun moveUp(index: Int): Int {
      if (index > 0) {
        val configuration = myConfigurations!![index]
        myConfigurations!!.removeAt(index)
        myConfigurations!!.add(index - 1, configuration)
        fireTableRowsUpdated(index - 1, index)
        return index - 1
      }

      return -1
    }

    fun moveDown(index: Int): Int {
      if (index < rowCount - 1) {
        val configuration = myConfigurations!![index]
        myConfigurations!!.removeAt(index)
        myConfigurations!!.add(index + 1, configuration)
        fireTableRowsUpdated(index, index + 1)
        return index + 1
      }

      return -1
    }

    override fun addRow() {
      val dialog = JetWashColorConfigurationEditDialog(null)
      dialog.show()

      if (dialog.exitCode == 0) {
        myConfigurations!!.add(dialog.configuration!!)
        val i = myConfigurations!!.size - 1
        fireTableRowsInserted(i, i)
      }
    }

    override fun removeRow(index: Int) {
      myConfigurations!!.removeAt(index)
      fireTableRowsDeleted(index, index)
    }

    override fun exchangeRows(oldIndex: Int, newIndex: Int) {
      myConfigurations!!.add(newIndex, myConfigurations!!.removeAt(oldIndex))
      fireTableRowsUpdated(Math.min(oldIndex, newIndex), Math.max(oldIndex, newIndex))
    }

    override fun canExchangeRows(oldIndex: Int, newIndex: Int): Boolean {
      return true
    }
  }

  private class ColorCellRenderer : DefaultTableCellRenderer() {
    override fun getTableCellRendererComponent(table: JTable?, value: Any, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
      return super.getTableCellRendererComponent(table, value, isSelected, false, row, column)
    }

    override fun setValue(value: Any?) {
      var icon: Icon? = null
      var text: String? = null
      if (value is JetWashColorConfiguration) {
        icon = getIcon(value)
        text = getText(value)
      }
      setIcon(icon)
      setText(text ?: "")
    }

    internal fun getIcon(configuration: JetWashColorConfiguration): Icon {
      val color = ColorUtil.fromHex(configuration.colorName)
      return JBUI.scale(ColorIcon(16, 13, color, true))
    }

    internal fun getText(configuration: JetWashColorConfiguration): String {
      return configuration.colorName
    }

  }

  companion object {
    private val NAME_COLUMN = 0
    private val COLOR_COLUMN = 1

    private fun copy(configurations: List<JetWashColorConfiguration>): MutableList<JetWashColorConfiguration> {
      val result = ArrayList<JetWashColorConfiguration>()
      for (c in configurations) {
        try {
          result.add(c.copy())
        } catch (e: CloneNotSupportedException) {
          assert(false) { "Should not happen!" }
        }

      }

      return result
    }
  }
}
