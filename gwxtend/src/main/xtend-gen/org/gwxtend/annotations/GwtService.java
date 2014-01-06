package org.gwxtend.annotations;

import org.eclipse.xtend.lib.macro.Active;
import org.gwxtend.annotations.GwtServiceProcessor;

@Active(GwtServiceProcessor.class)
public @interface GwtService {
  public String moduleName();
}
