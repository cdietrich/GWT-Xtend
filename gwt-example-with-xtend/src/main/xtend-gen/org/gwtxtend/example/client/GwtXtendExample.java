package org.gwtxtend.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import org.gwtxtend.annotations.GwtModule;

@GwtModule(moduleName = "GwtXtendExample")
@SuppressWarnings("all")
public class GwtXtendExample implements EntryPoint {
  public void onModuleLoad() {
    RootPanel _get = RootPanel.get();
    Label _label = new Label("Hello World!");
    _get.add(_label);
  }
}
