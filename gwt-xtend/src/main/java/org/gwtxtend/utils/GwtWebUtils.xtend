package org.gwtxtend.utils

import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration

class GwtWebUtils {
    
    private extension TransformationContext tContext
    private extension CodeGenerationContext cgContext
    
    new(TransformationContext tContext, CodeGenerationContext cgContext) {
        this.tContext = tContext
        this.cgContext = cgContext
    }

    def newWebXml(ClassDeclaration annotatedClass) '''
     <?xml version="1.0" encoding="UTF-8"?>
     <web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">
     
     </web-app>
    '''
    
    def getWebXml(ClassDeclaration annotatedClass) {
        return annotatedClass.webappFolder.append('WEB-INF/web.xml')
    }
    
    def getWebappFolder(ClassDeclaration annotatedClass) {
        return cgContext.getProjectFolder(annotatedClass.compilationUnit.filePath).append('src/main/webapp')
    }
}
