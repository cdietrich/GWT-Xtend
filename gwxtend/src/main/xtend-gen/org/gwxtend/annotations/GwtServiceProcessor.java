package org.gwxtend.annotations;

import java.io.InputStream;
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
import org.eclipse.xtext.xbase.lib.Extension;
import org.gwxtend.annotations.GwtService;
import org.gwxtend.utils.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@SuppressWarnings("all")
public class GwtServiceProcessor extends AbstractClassProcessor {
  @Extension
  private TransformationContext tContext;
  
  public void doTransform(final MutableClassDeclaration annotatedClass, @Extension final TransformationContext context) {
    this.tContext = context;
  }
  
  public void doGenerateCode(final ClassDeclaration annotatedClass, @Extension final CodeGenerationContext context) {
    CompilationUnit _compilationUnit = annotatedClass.getCompilationUnit();
    Path _filePath = _compilationUnit.getFilePath();
    final Path targetFolder = context.getTargetFolder(_filePath);
    String _modulePath = this.getModulePath(annotatedClass);
    final Path moduleFile = targetFolder.append(_modulePath);
    InputStream _contentsAsStream = context.getContentsAsStream(moduleFile);
    final Document document = XmlUtils.getDocument(_contentsAsStream);
    final Element servletNode = document.createElement("servlet");
    String _qualifiedName = annotatedClass.getQualifiedName();
    servletNode.setAttribute("class", _qualifiedName);
    document.appendChild(servletNode);
    boolean _exists = context.exists(moduleFile);
    if (_exists) {
      context.setContents(moduleFile, "Baum!!");
    } else {
      context.setContents(moduleFile, "Baum!!");
    }
  }
  
  private String getModulePath(final ClassDeclaration annotatedClass) {
    String _qualifiedName = annotatedClass.getQualifiedName();
    String _replace = _qualifiedName.replace(".server", "");
    String _replace_1 = _replace.replace(".", "/");
    AnnotationReference _gwtService = this.getGwtService(annotatedClass);
    Object _value = _gwtService.getValue("moduleName");
    String _plus = (_replace_1 + _value);
    return (_plus + "gwt.xml");
  }
  
  private AnnotationReference getGwtService(final ClassDeclaration annotatedClass) {
    TypeReference _newTypeReference = this.tContext.newTypeReference(GwtService.class);
    Type _type = _newTypeReference.getType();
    return annotatedClass.findAnnotation(_type);
  }
}
