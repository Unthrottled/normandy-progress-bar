package io.acari.n7.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@State(
    name = "NormandyUIConfig",
    storages = @Storage("normandy_ui_theme.xml")
)
public class NormandyUIConfig implements PersistentStateComponent<NormandyUIConfig>, Cloneable {

  // They are public so they can be serialized
  public String primaryThemeColor = "#FFFFFF";
  public String secondaryThemeColor = "#000000";
  public String jetWash = "";
  public String borderColor = "#EFEFEF";


  public NormandyUIConfig() {
  }

  public static Optional<NormandyUIConfig> getInstance() {
    return Optional.ofNullable(ServiceManager.getService(NormandyUIConfig.class));
  }

  public String getPrimaryThemeColor() {
    return primaryThemeColor;
  }

  public void setPrimaryThemeColor(String primaryThemeColor) {
    this.primaryThemeColor = primaryThemeColor;
  }

  public String getSecondaryThemeColor() {
    return secondaryThemeColor;
  }

  public void setSecondaryThemeColor(String secondaryThemeColor) {
    this.secondaryThemeColor = secondaryThemeColor;
  }

  public String getJetWash() {
    return jetWash;
  }

  public void setJetWash(String jetWash) {
    this.jetWash = jetWash;
  }

  public String getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(String borderColor) {
    this.borderColor = borderColor;
  }

  @Override
  public Object clone() {
    return XmlSerializerUtil.createCopy(this);
  }


  /**
   * Convenience method to reset settings
   */
  public void resetSettings() {
    primaryThemeColor = "";
    secondaryThemeColor = "";
    jetWash = "";
    borderColor = "";
  }


  /**
   * Get the state of MTConfig
   */
  @Nullable
  @Override
  public NormandyUIConfig getState() {
    return this;
  }

  /**
   * Load the state from XML
   *
   * @param state the MTConfig instance
   */
  @Override
  public void loadState(@NotNull final NormandyUIConfig state) {
    XmlSerializerUtil.copyBean(state, this);
  }


}
