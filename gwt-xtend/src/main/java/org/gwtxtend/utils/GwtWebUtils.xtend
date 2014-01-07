package org.gwtxtend.utils

import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration

class GwtWebUtils {
    
    private static val INSTANCE = new GwtWebUtils

    @Property private extension TransformationContext tContext
    @Property private extension CodeGenerationContext cgContext
    
    private new() {}

    def static newWebXml(ClassDeclaration annotatedClass, TransformationContext tContext,
                         CodeGenerationContext cgContext) {
        INSTANCE.TContext = tContext
        INSTANCE.cgContext = cgContext
        
        INSTANCE.createDefaultWebXmlFor(annotatedClass)                        
    }
    
    def private createDefaultWebXmlFor(ClassDeclaration annotatedClass) '''
     <?xml version="1.0" encoding="UTF-8"?>
     <web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
              version="3.0">
     
     </web-app>
    '''
}
