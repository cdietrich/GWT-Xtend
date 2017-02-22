package org.gwtxtend.example.client

import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.RootPanel
import org.eclipse.xtend2.lib.StringConcatenation
import org.gwtxtend.annotations.GwtModule

@GwtModule(moduleName = "GwtXtendExample")
class GwtXtendExample {
    
    override onModuleLoad() {
        RootPanel.get().add(new Label("Hello World!"))
        val StringConcatenation d = null;
    }
    
}