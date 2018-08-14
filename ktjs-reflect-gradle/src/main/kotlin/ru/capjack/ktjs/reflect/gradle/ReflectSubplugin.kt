package ru.capjack.ktjs.reflect.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.compile.AbstractCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinGradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import ru.capjack.ktjs.reflect.gradle.compiler.Const
import ru.capjack.ktjs.reflect.gradle.compiler.ReflectConfiguration

class ReflectSubplugin : KotlinGradleSubplugin<AbstractCompile> {
	
	override fun isApplicable(project: Project, task: AbstractCompile): Boolean {
		return ReflectPlugin.isEnabled(project)
	}
	
	override fun getCompilerPluginId(): String {
		return Const.PLUGIN_ID
	}
	
	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact("ru.capjack.gradle", "gradle-ktjs-reflect", ReflectPlugin.VERSION)
	}
	
	override fun apply(
		project: Project,
		kotlinCompile: AbstractCompile,
		javaCompile: AbstractCompile,
		variantData: Any?,
		androidProjectHandler: Any?,
		javaSourceSet: SourceSet?
	): List<SubpluginOption> {
		
		if (!ReflectPlugin.isEnabled(project)) return emptyList()
		
		val extension = project.extensions.findByType(ReflectExtension::class.java) ?: return emptyList()
		
		val options = mutableListOf<SubpluginOption>()
		
		for (configuration in extension.configurations) {
			options.add(SubpluginOption(configuration.optionName, configuration.optionValue))
		}
		
		return options
	}
	
	private val ReflectConfiguration.optionName: String
		get() = target.name.toLowerCase()
	
	private val ReflectConfiguration.optionValue: String
		get() = name + if (kinds.isEmpty()) "" else kinds.joinToString(";", "|") { it.name }
	
}