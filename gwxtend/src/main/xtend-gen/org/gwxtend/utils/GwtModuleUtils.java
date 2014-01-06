package org.gwxtend.utils;

import com.google.common.base.Objects;
import org.eclipse.xtend.lib.macro.CodeGenerationContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.gwxtend.annotations.GwtModule;

@SuppressWarnings("all")
public class GwtModuleUtils {
  @Extension
  private TransformationContext tcontext;
  
  @Extension
  private CodeGenerationContext cgContext;
  
  public GwtModuleUtils(final TransformationContext tContext, final CodeGenerationContext cgContext) {
    this.tcontext = tContext;
    this.cgContext = cgContext;
  }
  
  public CharSequence newModule(final ClassDeclaration annotatedClass) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<module rename-to=\"");
    String _moduleName = this.getModuleName(annotatedClass);
    _builder.append(_moduleName, "");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<!-- Inherit the core Web Toolkit stuff.                        -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<inherits name=\'com.google.gwt.user.User\' />");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- We need the JUnit module in the main module,               -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- otherwise eclipse complains (Google plugin bug?)           -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<inherits name=\'com.google.gwt.junit.JUnit\' />");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Inherit the default GWT style sheet.  You can change       -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- the theme of your GWT application by uncommenting          -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- any one of the following lines.                            -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<inherits name=\'com.google.gwt.user.theme.standard.Standard\' />");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- <inherits name=\'com.google.gwt.user.theme.chrome.Chrome\'/> -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- <inherits name=\'com.google.gwt.user.theme.dark.Dark\'/>     -->");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Other module inherits                                      -->");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Specify the app entry point class.                         -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<entry-point class=\'");
    String _qualifiedName = annotatedClass.getQualifiedName();
    _builder.append(_qualifiedName, "\t");
    _builder.append("\' />");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Specify the paths for translatable code                    -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<source path=\'client\' />");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<source path=\'shared\' />");
    _builder.newLine();
    _builder.newLine();
    _builder.append("</module>");
    _builder.newLine();
    return _builder;
  }
  
  private String getModuleName(final ClassDeclaration annotatedClass) {
    AnnotationReference _gwtModule = this.getGwtModule(annotatedClass);
    Object _value = _gwtModule.getValue("moduleName");
    final String moduleName = ((String) _value);
    boolean _or = false;
    boolean _equals = Objects.equal(moduleName, null);
    if (_equals) {
      _or = true;
    } else {
      int _length = moduleName.length();
      boolean _equals_1 = (_length == 0);
      _or = (_equals || _equals_1);
    }
    if (_or) {
      return annotatedClass.getSimpleName();
    }
    return moduleName;
  }
  
  private AnnotationReference getGwtModule(final ClassDeclaration annotatedClass) {
    TypeReference _newTypeReference = this.tcontext.newTypeReference(GwtModule.class);
    Type _type = _newTypeReference.getType();
    return annotatedClass.findAnnotation(_type);
  }
}
