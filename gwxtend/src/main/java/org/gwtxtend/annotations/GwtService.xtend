package org.gwtxtend.annotations

import org.eclipse.xtend.lib.macro.Active
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration

import static extension org.gwxtend.utils.XmlUtils.*

@Active(GwtServiceProcessor)
annotation GwtService {
	String moduleName
}

class GwtServiceProcessor extends AbstractClassProcessor {

	private extension TransformationContext tContext

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		tContext = context
	}

	override doGenerateCode(ClassDeclaration annotatedClass, extension CodeGenerationContext context) {
		val targetFolder = annotatedClass.compilationUnit.filePath.targetFolder
		val moduleFile = targetFolder.append(annotatedClass.modulePath)
		
		val document = moduleFile.contentsAsStream.document
		
		val servletNode = document.createElement('servlet')
		servletNode.setAttribute('class', annotatedClass.qualifiedName)
		document.appendChild(servletNode)
		
		if(moduleFile.exists) {
			// TODO:
			moduleFile.contents = "Baum!!"
		} else {
			moduleFile.contents = "Baum!!"	
		}
		
	}

	def private getModulePath(ClassDeclaration annotatedClass) {
		return annotatedClass.qualifiedName.replace('.server', '').replace('.', '/') +
			annotatedClass.gwtService.getValue('moduleName') + 'gwt.xml'
	}
	
	def private getGwtService(ClassDeclaration annotatedClass) {
		return annotatedClass.findAnnotation(GwtService.newTypeReference().type)
	}
}
