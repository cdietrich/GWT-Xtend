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

    override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
        tContext = context
        annotatedClass.implementedInterfaces = #[EntryPoint.newTypeReference()]
    }

    override doGenerateCode(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        moduleUtils = new GwtModuleUtils(tContext, context)
        htmlUtils = new GwtHtmlUtils(moduleUtils)

        createModuleFile(annotatedClass, context)
        createWebXml(annotatedClass, context)
        createWelcomePage(annotatedClass, context)
    }

    def createModuleFile(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        val targetFolder = annotatedClass.compilationUnit.filePath.targetFolder
        val moduleFile = targetFolder.append(annotatedClass.modulePath)

        if (!moduleFile.exists) {
            moduleFile.contents = annotatedClass.createDefaultModule
        }
    }

    def private getModulePath(ClassDeclaration annotatedClass) {
        return annotatedClass.qualifiedName.replace('.', '/').replace(annotatedClass.simpleName,
            annotatedClass.moduleName) + ".gwt.xml"
    }

    def createWebXml(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        val webXml = annotatedClass.getWebappFolder(context).append("WEB-INF/web.xml")
        webXml.contents = GwtWebUtils.newWebXml(annotatedClass, tContext, context)
    }

    def createWelcomePage(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        val welcomePage = annotatedClass.getWebappFolder(context).append(annotatedClass.welcomePage)
        if (!welcomePage.exists) {
            welcomePage.contents = annotatedClass.newWelcomePage 
        }
    }

    def private getWebappFolder(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
        return annotatedClass.compilationUnit.filePath.projectFolder.append("src/main/webapp")
    }

    def private getWelcomePage(ClassDeclaration annotatedClass) {
        val welcomePage = annotatedClass.gwtModule.getValue('welcomePage') as String
        if (welcomePage == null || welcomePage.length == 0) {
            return annotatedClass.simpleName + '.html'
        } else if (welcomePage.contains('.')) {
            return welcomePage
        }
        return welcomePage + '.html'
    }
}
