package org.gwxtend.annotations

import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import com.google.gwt.core.client.EntryPoint
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.gwxtend.utils.GwtModuleUtils

@Active(GwtModuleProcessor)
annotation GwtModule {
	String moduleName = ""
	String welcomePage = ""
}

class GwtModuleProcessor extends AbstractClassProcessor {

	private extension TransformationContext tContext
	private extension GwtModuleUtils moduleUtils

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		tContext = context

		annotatedClass.implementedInterfaces = #[EntryPoint.newTypeReference()]
	}

	override doGenerateCode(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
		moduleUtils = new GwtModuleUtils(tContext, context)
		createModuleFile(annotatedClass, context)
		createWebXml(annotatedClass, context)
		createWelcomePage(annotatedClass, context)
	}

	def createModuleFile(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
		val targetFolder = annotatedClass.compilationUnit.filePath.targetFolder
		val moduleFile = targetFolder.append(annotatedClass.modulePath)

		if (!moduleFile.exists) {
			moduleFile.contents = annotatedClass.newModule
		} else {
			annotatedClass.addError("Specified Module already exists.")
		}
	}

	def private getModulePath(ClassDeclaration annotatedClass) {
		return annotatedClass.qualifiedName.replace('.', '/').replace(annotatedClass.simpleName,
			annotatedClass.moduleName) + ".gwt.xml"
	}

	def private getModuleName(ClassDeclaration annotatedClass) {
		val moduleName = annotatedClass.gwtModule.getValue('moduleName') as String
		if (moduleName == null || moduleName.length == 0) {
			return annotatedClass.simpleName
		}
		return moduleName
	}

	def createWebXml(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
		val webXml = annotatedClass.getWebappFolder(context).append("WEB-INF/web.xml")
		webXml.contents = '''
			<?xml version="1.0" encoding="UTF-8"?>
			<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN" "http://java.sun.com/dtd/web-app_2_3.dtd">
				
			<web-app>
				
				<!-- Default page to serve -->
				<welcome-file-list>
						<welcome-file>«annotatedClass.welcomePage»</welcome-file>
				</welcome-file-list>
				
			</web-app>
		'''
	}

	def createWelcomePage(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
		val welcomePage = annotatedClass.getWebappFolder(context).append(annotatedClass.welcomePage)
		if (!welcomePage.exists) {
			welcomePage.contents = '''
				<!doctype html>
				<!-- The DOCTYPE declaration above will set the    -->
				<!-- browser's rendering engine into               -->
				<!-- "Standards Mode". Replacing this declaration  -->
				<!-- with a "Quirks Mode" doctype may lead to some -->
				<!-- differences in layout.                        -->
				
				<html>
					<head>
						<meta http-equiv="content-type" content="text/html; charset=UTF-8">
				
				 		<!--                                                               -->
				 		<!-- Consider inlining CSS to reduce the number of requested files -->
				 		<!--                                                               -->
				 		<link type="text/css" rel="stylesheet" href="gwxtend.css">
				
				 		<!--                                           -->
				 		<!-- Any title is fine                         -->
				 		<!--                                           -->
				 		<title>«annotatedClass.moduleName»</title>
				
				 		<!--                                           -->
				 		<!-- This script loads your compiled module.   -->
				 		<!-- If you add any GWT meta tags, they must   -->
				 		<!-- be added before this line.                -->
				 		<!--                                           -->
				 		<script type="text/javascript" language="javascript" src="gwxtend/gwxtend.nocache.js"></script>
					</head>
				
					<!--                                           -->
					<!-- The body can have arbitrary html, or      -->
					<!-- you can leave the body empty if you want  -->
					<!-- to create a completely dynamic UI.        -->
					<!--                                           -->
					<body>
				
						<!-- OPTIONAL: include this if you want history support -->
						<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
				
				 		<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
				 		<noscript>
				 		<div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
				 			Your web browser must have JavaScript enabled
				 			in order for this application to display correctly.
				 		</div>
						</noscript>
				
					</body>
				</html>
					
			'''
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

	def private getGwtModule(ClassDeclaration annotatedClass) {
		return annotatedClass.findAnnotation(GwtModule.newTypeReference().type)
	}
}
