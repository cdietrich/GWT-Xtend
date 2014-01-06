package org.gwxtend.annotations;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.EntryPoint;
import java.util.Collections;
import org.eclipse.xtend.lib.macro.AbstractClassProcessor;
import org.eclipse.xtend.lib.macro.CodeGenerationContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.AnnotationReference;
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.CompilationUnit;
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration;
import org.eclipse.xtend.lib.macro.declaration.Type;
import org.eclipse.xtend.lib.macro.declaration.TypeReference;
import org.eclipse.xtend.lib.macro.file.Path;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.gwxtend.annotations.GwtModule;
import org.gwxtend.utils.GwtModuleUtils;

@SuppressWarnings("all")
public class GwtModuleProcessor extends AbstractClassProcessor {
  @Extension
  private TransformationContext tContext;
  
  @Extension
  private GwtModuleUtils moduleUtils;
  
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    this.tContext = context;
    TypeReference _newTypeReference = context.newTypeReference(EntryPoint.class);
    annotatedClass.setImplementedInterfaces(Collections.<TypeReference>unmodifiableList(Lists.<TypeReference>newArrayList(_newTypeReference)));
  }
  
  public void doGenerateCode(final ClassDeclaration annotatedClass, @Extension final CodeGenerationContext context) {
    GwtModuleUtils _gwtModuleUtils = new GwtModuleUtils(this.tContext, context);
    this.moduleUtils = _gwtModuleUtils;
    this.createModuleFile(annotatedClass, context);
    this.createWebXml(annotatedClass, context);
    this.createWelcomePage(annotatedClass, context);
  }
  
  public void createModuleFile(final ClassDeclaration annotatedClass, @Extension final CodeGenerationContext context) {
    CompilationUnit _compilationUnit = annotatedClass.getCompilationUnit();
    Path _filePath = _compilationUnit.getFilePath();
    final Path targetFolder = context.getTargetFolder(_filePath);
    String _modulePath = this.getModulePath(annotatedClass);
    final Path moduleFile = targetFolder.append(_modulePath);
    boolean _exists = context.exists(moduleFile);
    boolean _not = (!_exists);
    if (_not) {
      CharSequence _newModule = this.moduleUtils.newModule(annotatedClass);
      context.setContents(moduleFile, _newModule);
    } else {
      this.tContext.addError(annotatedClass, "Specified Module already exists.");
    }
  }
  
  private String getModulePath(final ClassDeclaration annotatedClass) {
    String _qualifiedName = annotatedClass.getQualifiedName();
    String _replace = _qualifiedName.replace(".", "/");
    String _simpleName = annotatedClass.getSimpleName();
    String _moduleName = this.getModuleName(annotatedClass);
    String _replace_1 = _replace.replace(_simpleName, _moduleName);
    return (_replace_1 + ".gwt.xml");
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
  
  public void createWebXml(final ClassDeclaration annotatedClass, @Extension final CodeGenerationContext context) {
    Path _webappFolder = this.getWebappFolder(annotatedClass, context);
    final Path webXml = _webappFolder.append("WEB-INF/web.xml");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN\" \"http://java.sun.com/dtd/web-app_2_3.dtd\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("<web-app>");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Default page to serve -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<welcome-file-list>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<welcome-file>");
    String _welcomePage = this.getWelcomePage(annotatedClass);
    _builder.append(_welcomePage, "\t\t\t");
    _builder.append("</welcome-file>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</welcome-file-list>");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("</web-app>");
    _builder.newLine();
    context.setContents(webXml, _builder);
  }
  
  public void createWelcomePage(final ClassDeclaration annotatedClass, @Extension final CodeGenerationContext context) {
    Path _webappFolder = this.getWebappFolder(annotatedClass, context);
    String _welcomePage = this.getWelcomePage(annotatedClass);
    final Path welcomePage = _webappFolder.append(_welcomePage);
    boolean _exists = context.exists(welcomePage);
    boolean _not = (!_exists);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<!doctype html>");
      _builder.newLine();
      _builder.append("<!-- The DOCTYPE declaration above will set the    -->");
      _builder.newLine();
      _builder.append("<!-- browser\'s rendering engine into               -->");
      _builder.newLine();
      _builder.append("<!-- \"Standards Mode\". Replacing this declaration  -->");
      _builder.newLine();
      _builder.append("<!-- with a \"Quirks Mode\" doctype may lead to some -->");
      _builder.newLine();
      _builder.append("<!-- differences in layout.                        -->");
      _builder.newLine();
      _builder.newLine();
      _builder.append("<html>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<head>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
      _builder.newLine();
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!--                                                               -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!-- Consider inlining CSS to reduce the number of requested files -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!--                                                               -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"gwxtend.css\">");
      _builder.newLine();
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!--                                           -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!-- Any title is fine                         -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!--                                           -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<title>");
      String _moduleName = this.getModuleName(annotatedClass);
      _builder.append(_moduleName, " \t\t");
      _builder.append("</title>");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!--                                           -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!-- This script loads your compiled module.   -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!-- If you add any GWT meta tags, they must   -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!-- be added before this line.                -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!--                                           -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<script type=\"text/javascript\" language=\"javascript\" src=\"gwxtend/gwxtend.nocache.js\"></script>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</head>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<!--                                           -->");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<!-- The body can have arbitrary html, or      -->");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<!-- you can leave the body empty if you want  -->");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<!-- to create a completely dynamic UI.        -->");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<!--                                           -->");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<body>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<!-- OPTIONAL: include this if you want history support -->");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<iframe src=\"javascript:\'\'\" id=\"__gwt_historyFrame\" tabIndex=\'-1\' style=\"position:absolute;width:0;height:0;border:0\"></iframe>");
      _builder.newLine();
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<noscript>");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("<div style=\"width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif\">");
      _builder.newLine();
      _builder.append(" \t\t\t");
      _builder.append("Your web browser must have JavaScript enabled");
      _builder.newLine();
      _builder.append(" \t\t\t");
      _builder.append("in order for this application to display correctly.");
      _builder.newLine();
      _builder.append(" \t\t");
      _builder.append("</div>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</noscript>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</body>");
      _builder.newLine();
      _builder.append("</html>");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      context.setContents(welcomePage, _builder);
    }
  }
  
  private Path getWebappFolder(final ClassDeclaration annotatedClass, @Extension final CodeGenerationContext context) {
    CompilationUnit _compilationUnit = annotatedClass.getCompilationUnit();
    Path _filePath = _compilationUnit.getFilePath();
    Path _projectFolder = context.getProjectFolder(_filePath);
    return _projectFolder.append("src/main/webapp");
  }
  
  private String getWelcomePage(final ClassDeclaration annotatedClass) {
    AnnotationReference _gwtModule = this.getGwtModule(annotatedClass);
    Object _value = _gwtModule.getValue("welcomePage");
    final String welcomePage = ((String) _value);
    boolean _or = false;
    boolean _equals = Objects.equal(welcomePage, null);
    if (_equals) {
      _or = true;
    } else {
      int _length = welcomePage.length();
      boolean _equals_1 = (_length == 0);
      _or = (_equals || _equals_1);
    }
    if (_or) {
      String _simpleName = annotatedClass.getSimpleName();
      return (_simpleName + ".html");
    } else {
      boolean _contains = welcomePage.contains(".");
      if (_contains) {
        return welcomePage;
      }
    }
    return (welcomePage + ".html");
  }
  
  private AnnotationReference getGwtModule(final ClassDeclaration annotatedClass) {
    TypeReference _newTypeReference = this.tContext.newTypeReference(GwtModule.class);
    Type _type = _newTypeReference.getType();
    return annotatedClass.findAnnotation(_type);
  }
}
