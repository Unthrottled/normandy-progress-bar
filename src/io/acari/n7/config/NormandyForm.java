/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2018 Chris Magnussen and Elior Boukhobza
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package io.acari.n7.config;

import com.intellij.ui.ColorPanel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

@SuppressWarnings({"OverlyLongMethod",
    "UseDPIAwareInsets",
    "MagicNumber",
    "DuplicateStringLiteralInspection",
    "FieldCanBeLocal",
    "OverlyLongLambda",
    "ClassWithTooManyFields",
    "PublicMethodNotExposedInInterface",
    "Duplicates",
    "ClassWithTooManyMethods"})
public final class NormandyForm {

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner non-commercial license
  private JPanel content;
  private JLabel explLabel;
  private JTextArea expTextArea;
  private JSeparator separator1;
  private JLabel backgroundColorLabel;
  private ColorPanel backgroundColor;
  private JLabel foregroundColorLabel;
  private ColorPanel foregroundColor;
  private JLabel labelColorLabel;
  private ColorPanel labelColor;
  private JLabel selectionBackgroundColorLabel;
  private ColorPanel selectionBackgroundColor;
  private JButton resetTabDefaultsBtn;
  @SuppressWarnings("FeatureEnvy")
  public NormandyForm() {
    initComponents();

    // Reset tab defaults
    resetTabDefaultsBtn.addActionListener(e -> {
      setSelectionBackgroundColor(MTCustomDefaults.selectionBackgroundColor);
      setTextColor(MTCustomDefaults.textColor);
      setForegroundColor(MTCustomDefaults.foregroundColor);
      setBackgroundColor(MTCustomDefaults.backgroundColor);
    });
  }

  public JComponent getContent() {
    return content;
  }

  public Color getBackgroundColor() {
    return backgroundColor.getSelectedColor();
  }

  private void setBackgroundColor(final Color backgroundColor) {
    this.backgroundColor.setSelectedColor(backgroundColor);
  }

  public Color getForegroundColor() {
    return foregroundColor.getSelectedColor();
  }

  private void setForegroundColor(final Color foregroundColor) {
    this.foregroundColor.setSelectedColor(foregroundColor);
  }

  public Color getTextColor() {
    return labelColor.getSelectedColor();
  }

  private void setTextColor(final Color labelColor) {
    this.labelColor.setSelectedColor(labelColor);
  }

  public Color getSelectionBackgroundColor() {
    return selectionBackgroundColor.getSelectedColor();
  }

  private void setSelectionBackgroundColor(final Color selectionBackgroundColor) {
    this.selectionBackgroundColor.setSelectedColor(selectionBackgroundColor);
  }

  // JFormDesigner - End of variables declaration  //GEN-END:variables

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner non-commercial license
    content = new JPanel();
    final JPanel panel1 = new JPanel();
    explLabel = new JLabel();
    expTextArea = new JTextArea();
    separator1 = new JSeparator();
    backgroundColorLabel = new JLabel();
    backgroundColor = new ColorPanel();
    foregroundColorLabel = new JLabel();
    foregroundColor = new ColorPanel();
    labelColorLabel = new JLabel();
    labelColor = new ColorPanel();
    selectionBackgroundColorLabel = new JLabel();
    selectionBackgroundColor = new ColorPanel();
    resetTabDefaultsBtn = new JButton();


    //======== content ========
    {
      content.setAutoscrolls(true);
      content.setRequestFocusEnabled(false);
      content.setVerifyInputWhenFocusTarget(false);
      content.setBorder(null);
      content.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));

      //======== panel1 ========
      {
        panel1.setBorder(new TitledBorder(new EtchedBorder(), ("MTForm.customColorsTitle")));
        panel1.setLayout(new GridLayoutManager(18, 2, new Insets(0, 3, 0, 0), -1, -1));

        //---- explLabel ----
        explLabel.setText(("MTForm.explLabel.text"));
        explLabel.setForeground(UIManager.getColor("#FFFFFF"));
        panel1.add(explLabel, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- expTextArea ----
        expTextArea.setBackground(UIManager.getColor("Panel.background"));
        expTextArea.setFont(UIManager.getFont("Panel.font"));
        expTextArea.setText(("MTForm.expTextArea.text"));
        expTextArea.setRows(2);
        expTextArea.setWrapStyleWord(true);
        expTextArea.setEditable(false);
        expTextArea.setBorder(null);
        panel1.add(expTextArea, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        panel1.add(separator1, new GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- backgroundColorLabel ----
        backgroundColorLabel.setText(("MTColorForm.background"));
        backgroundColorLabel.setToolTipText(("MTCustomThemeForm.backgroundColor.toolTipText"));
        panel1.add(backgroundColorLabel, new GridConstraints(3, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        panel1.add(backgroundColor, new GridConstraints(3, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- foregroundColorLabel ----
        foregroundColorLabel.setText(("MTForm.foregroundColorLabel.text"));
        foregroundColorLabel.setToolTipText(("MTForm.foregroundColorLabel.toolTipText"));
        panel1.add(foregroundColorLabel, new GridConstraints(4, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        panel1.add(foregroundColor, new GridConstraints(4, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- labelColorLabel ----
        labelColorLabel.setText(("MTForm.labelColorLabel.text"));
        labelColorLabel.setToolTipText(("MTForm.labelColorLabel.toolTipText"));
        panel1.add(labelColorLabel, new GridConstraints(5, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        panel1.add(labelColor, new GridConstraints(5, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

        //---- selectionBackgroundColorLabel ----
        selectionBackgroundColorLabel.setText(("MTForm.selectionBackgroundColorLabel.text"));
        selectionBackgroundColorLabel.setToolTipText(("MTForm.selectionBackgroundColorLabel.toolTipText"));
        panel1.add(selectionBackgroundColorLabel, new GridConstraints(6, 0, 1, 1,
            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        panel1.add(selectionBackgroundColor, new GridConstraints(6, 1, 1, 1,
            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));

      }
      content.add(panel1, new GridConstraints(0, 0, 1, 1,
          GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));

      //---- resetTabDefaultsBtn ----
      resetTabDefaultsBtn.setText(("mt.resetCustomTheme.title"));
      resetTabDefaultsBtn.setToolTipText(("mt.resetdefaults.tooltip"));
      panel1.add(resetTabDefaultsBtn, new GridConstraints(17, 0, 1, 1,
          GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
          null, null, null));
    }
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  /**
   * Default colors for Custom theme
   */
  @SuppressWarnings({
      "PublicInnerClass",
      "ClassWithTooManyFields"})
  public enum MTCustomDefaults {;
    public static final ColorUIResource selectionBackgroundColor = new ColorUIResource(0x546E7A);
    public static final ColorUIResource textColor = new ColorUIResource(0x607D8B);
    public static final ColorUIResource foregroundColor = new ColorUIResource(0xB0BEC5);
    public static final ColorUIResource backgroundColor = new ColorUIResource(0x263238);
  }

}
