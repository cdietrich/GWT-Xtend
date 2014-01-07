package org.gwtxtend.example.client

import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.RootPanel
import org.gwtxtend.annotations.GwtModule

@GwtModule(moduleName = "GwtXtendExample")
class GwtXtendExample {
    
    override onModuleLoad() {
        RootPanel.get().add(new Label("Hello World!"))
    }
    
}