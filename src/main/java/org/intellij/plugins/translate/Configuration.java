package org.intellij.plugins.translate;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.util.xmlb.XmlSerializerUtil;
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

    public static final String CONFIGURATION_LOCATION;

    static {
        CONFIGURATION_LOCATION = System.getProperty("user.home");
    }

    private ConfigurationForm form;
    private String langFrom = Languages.ENGLISH;
    private String langTo = Languages.RUSSIAN;

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
        return "Yandex translate";
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
        XmlSerializerUtil.copyBean(state, this);
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
        if (form == null) {
            form = new ConfigurationForm();
        }

        return null;
        //form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return form != null /*&& form.isModified(this)*/;
    }

    /**
     * Stores settings from form to configuration bean.
     *
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        if (form != null) {
            //form.save(this);
        }
    }

    /**
     * Restores form values from configuration.
     */
    @Override
    public void reset() {
        if (form != null) {
            //form.load(this);
        }
    }

    /**
     * Disposes UI resource.
     */
    @Override
    public void disposeUIResources() {
        form = null;
    }
}
