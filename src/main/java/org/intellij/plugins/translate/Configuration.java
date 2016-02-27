package org.intellij.plugins.translate;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

@State(
        name = Configuration.COMPONENT_NAME,
        storages = {@Storage(id = "translate", file = "$PROJECT_FILE$")}
)
public final class Configuration implements ProjectComponent,
        Configurable, PersistentStateComponent<Configuration> {

    public static final String COMPONENT_NAME = "Translate.Configuration";
    public static final String NAME = "Yandex.Translator";
    public static final String CONFIGURATION_LOCATION;

    static {
        CONFIGURATION_LOCATION = System.getProperty("user.home");
    }

    private String langFrom = null;
    private String langTo = null;

    public String getFrom() {
        return langFrom;
    }

    public String getTo() {
        return langTo;
    }

    public void setLangPair(final String from, final String to) {
        langFrom = from;
        langTo = to;
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @Nls
    @Override
    public String getDisplayName() {
        return NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @NotNull
    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Nullable
    @Override
    public Configuration getState() {
        return this;
    }

    @Override
    public void loadState(Configuration state) {
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return null;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }
}
