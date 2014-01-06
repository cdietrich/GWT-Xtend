package org.gwxtend.annotations;

import org.eclipse.xtend.lib.macro.Active;
import org.gwxtend.annotations.GwtModuleProcessor;

@Active(GwtModuleProcessor.class)
public @interface GwtModule {
  public String moduleName() default "";
  public String welcomePage() default "";
}
