package io.unthrottled.n7.config.ui;

import com.intellij.ui.ColorPanel;
import com.intellij.ui.scale.JBUIScale;
import io.unthrottled.n7.theme.ThemeConfigurations;
import io.unthrottled.n7.theme.ThemeDefaults;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Dimension;

public class NormandyConfigUI {
  private JCheckBox customBackgroundCheckbox;
  private JCheckBox useThemeAccentCheckBox;
  private JCheckBox rainbowModeCheckBox;
  private JCheckBox transparentBackgroundCheckBox;
  private JButton restoreDefaultsButton;
  private JPanel rootPanel;
  private ColorPanel primaryColorPanel;
  private ColorPanel secondaryColorPanel;
  private ColorPanel contrailColorPanel;
  private ColorPanel backgroundColorPanel;
  private JProgressBar displayProgressBar;

  private final ThemeConfigurations themeConfigurations;
  private final ThemeConfigurations myThemeConfigurations;

  public NormandyConfigUI(ThemeConfigurations themeConfigurations) {
    this.themeConfigurations = themeConfigurations;
    myThemeConfigurations = themeConfigurations.duplicate();
  }

  public ThemeConfigurations getCurrentThemeConfig() {
    return myThemeConfigurations;
  }

  private void createUIComponents() {
    var primaryColor = new ColorPanel();
    primaryColor.setSelectedColor(themeConfigurations.getPrimaryColor());
    primaryColor.addActionListener((e) -> {
      myThemeConfigurations.setPrimaryColor(primaryColor.getSelectedColor());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {
      });
    });
    primaryColorPanel = primaryColor;

    var secondaryColor = new ColorPanel();
    secondaryColor.setSelectedColor(themeConfigurations.getSecondaryColor());
    secondaryColor.addActionListener(e -> {
      myThemeConfigurations.setSecondaryColor(secondaryColor.getSelectedColor());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {
      });
    });
    secondaryColorPanel = secondaryColor;

    var contrailColor = new ColorPanel();
    contrailColor.setSelectedColor(themeConfigurations.getContrail());
    contrailColor.addActionListener(a -> {
      myThemeConfigurations.setContrail(contrailColor.getSelectedColor());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {
      });
    });
    contrailColorPanel = contrailColor;

    var customBackgroundColor = new ColorPanel();
    customBackgroundColor.setSelectedColor(themeConfigurations.getBackgroundColor());
    customBackgroundColor.addActionListener(a -> {
      myThemeConfigurations.setBackgroundColor(customBackgroundColor.getSelectedColor());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {
      });
    });
    backgroundColorPanel = customBackgroundColor;

    displayProgressBar = createLoadingIndicator(true, Color.GREEN, false);
  }

  private JProgressBar createLoadingIndicator(Boolean indeterminate,
                                              Color foreground,
                                              Boolean modeless) {
    var progress = new JProgressBar(0, 100);
    progress.setIndeterminate(indeterminate);
    progress.setValue(0);
    progress.setForeground(foreground);
    if (modeless) {
      progress.putClientProperty("ProgressBar.stripeWidth", 2);
    }

    progress.setPreferredSize(new Dimension(500, JBUIScale.scale(25)));

    return progress;
  }

  public JPanel getRootPanel() {
    initializeComponents();
    return rootPanel;
  }

  private void initializeComponents() {
    customBackgroundCheckbox.addActionListener(a -> {
      myThemeConfigurations.setCustomBackground(customBackgroundCheckbox.isSelected());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {});
    });

    useThemeAccentCheckBox.addActionListener(a -> {
      myThemeConfigurations.setShouldUseThemeAccents(useThemeAccentCheckBox.isSelected());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {});
    });

    rainbowModeCheckBox.addActionListener(a -> {
      myThemeConfigurations.setRainbowMode(rainbowModeCheckBox.isSelected());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {});
    });

    transparentBackgroundCheckBox.addActionListener(a -> {
      myThemeConfigurations.setTransparentBackground(transparentBackgroundCheckBox.isSelected());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {});
    });

    restoreDefaultsButton.addActionListener(a -> {
      myThemeConfigurations.setPrimaryColor(ThemeDefaults.INSTANCE.getPrimaryColor());
      myThemeConfigurations.setSecondaryColor(ThemeDefaults.INSTANCE.getSecondaryColor());
      myThemeConfigurations.setContrail(ThemeDefaults.INSTANCE.getContrailColor());
      ConfigurationManager.INSTANCE.applyConfigurations(myThemeConfigurations, () -> {});
    });
  }

  public boolean isModified() {
    return !myThemeConfigurations.equals(themeConfigurations);
  }
}
