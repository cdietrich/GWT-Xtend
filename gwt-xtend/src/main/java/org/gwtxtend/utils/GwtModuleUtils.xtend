package org.gwtxtend.utils

import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.gwtxtend.annotations.GwtModule
import org.eclipse.xtend.lib.macro.TransformationContext

class GwtModuleUtils {

    private extension TransformationContext tContext
    private extension CodeGenerationContext cgContext

    new(TransformationContext tContext, CodeGenerationContext cgContext) {
        this.tContext = tContext
        this.cgContext = cgContext
    }

    def createDefaultModule(ClassDeclaration annotatedClass) '''
        <?xml version="1.0" encoding="UTF-8"?>
        <module rename-to="«annotatedClass.moduleName»">
            <!-- Inherit the core Web Toolkit stuff.                        -->
            <inherits name='com.google.gwt.user.User' />
        
            <!-- We need the JUnit module in the main module,               -->
            <!-- otherwise eclipse complains (Google plugin bug?)           -->
            <inherits name='com.google.gwt.junit.JUnit' />
        
            <!-- Inherit the default GWT style sheet.  You can change       -->
            <!-- the theme of your GWT application by uncommenting          -->
            <!-- any one of the following lines.                            -->
            <inherits name='com.google.gwt.user.theme.standard.Standard' />
            <!-- <inherits name='com.google.gwt.user.theme.chrome.Chrome'/> -->
            <!-- <inherits name='com.google.gwt.user.theme.dark.Dark'/>     -->
        
            <!-- Other module inherits                                      -->
            <inherits name='org.eclipse.xtend.lib.Lib' />
        
            <!-- Specify the app entry point class.                         -->
            <entry-point class='«annotatedClass.qualifiedName»' />
        
            <!-- Specify the paths for translatable code                    -->
            <source path='client' />
            <source path='shared' />
        
        </module>
    '''

    def getModuleName(ClassDeclaration annotatedClass) {
        val moduleName = annotatedClass.gwtModule.getValue('moduleName') as String
        if (moduleName == null || moduleName.length == 0) {
            return annotatedClass.simpleName
        }
        return moduleName
    }
    
    def getWelcomePage(ClassDeclaration annotatedClass) {
        val welcomePage = annotatedClass.gwtModule.getValue('welcomePage') as String
        if (welcomePage == null || welcomePage.length == 0) {
            return annotatedClass.simpleName + '.html'
        } else if (welcomePage.contains('.')) {
            return welcomePage
        }
        return welcomePage + '.html'
    }
    
    def private getGwtModule(ClassDeclaration annotatedClass) {
        return annotatedClass.findAnnotation(GwtModule.newTypeReference().type)
    }
}
