package org.gwtxtend.annotations

import com.google.gwt.core.client.EntryPoint
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.gwtxtend.utils.GwtHtmlUtils
import org.gwtxtend.utils.GwtModuleUtils
import org.gwtxtend.utils.GwtWebUtils

@Active(GwtModuleProcessor)
annotation GwtModule {
    String moduleName = ""
    String welcomePage = ""
}

class GwtModuleProcessor extends AbstractClassProcessor {

    private extension TransformationContext tContext
    private extension GwtModuleUtils moduleUtils
    private extension GwtHtmlUtils htmlUtils
    private extension GwtWebUtils webUtils

    override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
        tContext = context
        annotatedClass.implementedInterfaces = #[EntryPoint.newTypeReference()]
    }

    override doGenerateCode(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        moduleUtils = new GwtModuleUtils(tContext, context)
        htmlUtils = new GwtHtmlUtils()
        webUtils = new GwtWebUtils(tContext, context)

        createModuleFile(annotatedClass, context)
        createWebXml(annotatedClass, context)
        createWelcomePage(annotatedClass, context)
    }

    def createModuleFile(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        val targetFolder = annotatedClass.compilationUnit.filePath.targetFolder
        val moduleFile = targetFolder.append(annotatedClass.modulePath.toString)

        if (!moduleFile.exists) {
            moduleFile.contents = annotatedClass.createDefaultModule
        }
    }

    def private getModulePath(ClassDeclaration annotatedClass) '''
        «annotatedClass.qualifiedName.replace('.', '/').replace('client', '').replace(annotatedClass.simpleName,
            annotatedClass.moduleName)».gwt.xml
    '''
    def createWebXml(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        val webXml = annotatedClass.webXml
        if(!webXml.exists) {
            webXml.contents = annotatedClass.newWebXml 
        }
    }

    def createWelcomePage(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        val welcomePage = annotatedClass.webappFolder.append(annotatedClass.welcomePage)
        if (!welcomePage.exists) {
            welcomePage.contents = annotatedClass.newWelcomePage(annotatedClass.moduleName)
        }
    }
}
