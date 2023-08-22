@file:OptIn(ExperimentalAnvilApi::class)

package dev.danperez.injectorgen

import dagger.Module
import com.google.auto.service.AutoService
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.api.AnvilContext
import com.squareup.anvil.compiler.api.CodeGenerator
import com.squareup.anvil.compiler.api.GeneratedFile
import com.squareup.anvil.compiler.api.createGeneratedFile
import com.squareup.anvil.compiler.internal.asClassName
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.fqName
import com.squareup.anvil.compiler.internal.reference.*
import com.squareup.anvil.compiler.internal.safePackageString
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import dagger.Binds
import dagger.MembersInjector
import dagger.multibindings.IntoMap
import dev.danperez.scopes.codegen.AnvilInjector
import dev.danperez.scopes.codegen.InjectWith
import dev.danperez.scopes.codegen.BindingKey
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import java.io.File
import javax.inject.Inject

@ExperimentalAnvilApi
@AutoService(CodeGenerator::class)
class InjectorAndBinderCodeGenerator: CodeGenerator
{
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(InjectWith::class.fqName) }
            .map {
                generateAnvilInjectorFile(
                    codeGenDir = codeGenDir,
                    clazz = it,
                )
            }
            .toList()
    }


    private fun generateAnvilInjectorFile(
        codeGenDir: File,
        clazz: ClassReference.Psi,
    ): GeneratedFile
    {
        val classId = clazz.generateClassName(suffix = "_AnvilInjector")
        val packageName = classId.packageFqName.safePackageString()
        val className = classId.relativeClassName.asString()

        val scope = clazz.annotations
            .find { it.fqName == InjectWith::class.fqName }!!
            .scope()

        val content = FileSpec.buildFile(packageName, className) {
            addType( generateAnvilInjectorClass( clazz ) )
            addType( generateBinderClass(clazz, scope) )
        }

        return createGeneratedFile(codeGenDir, packageName, className, content)
    }

    private fun generateAnvilInjectorClass(
        clazz: ClassReference.Psi,
    ): TypeSpec
    {
        val classId = clazz.generateClassName(suffix = "_AnvilInjector")
        val classType: TypeName = clazz.asTypeName()
        val anvilInjectorClass = classId.asClassName()

        return TypeSpec.classBuilder(anvilInjectorClass)
            .addSuperinterface( AnvilInjector::class.asClassName().parameterizedBy(classType) )
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addAnnotation(Inject::class)
                    .addParameter(
                        ParameterSpec.builder("injector", MembersInjector::class.asClassName().parameterizedBy(classType))
                            .build()
                    )
                    .build()
            )
            .addProperty(
                PropertySpec.builder("injector", MembersInjector::class.asClassName().parameterizedBy(classType), KModifier.OVERRIDE)
                    .initializer("injector")
                    .build()
            )
            .build()
    }

    private fun generateBinderClass(
        clazz: ClassReference.Psi,
        scope: ClassReference,
    ): TypeSpec
    {
        val classId = clazz.generateClassName(suffix = "_AnvilInjectorBinder")
        val bindingTypeImpl: TypeName = clazz.generateClassName(suffix = "_AnvilInjector").asClassName()
        val anvilInjectorBinderClass = classId.asClassName()

        return TypeSpec.interfaceBuilder(anvilInjectorBinderClass)
            .addAnnotation(Module::class)
            .addAnnotation(
                AnnotationSpec.builder(ContributesTo::class)
                    .addMember("%T::class", scope.asClassName())
                    .build()
            )
            .addFunction(
                FunSpec.builder("bind")
                    .addModifiers(KModifier.ABSTRACT)
                    .addAnnotation(Binds::class)
                    .addAnnotation(IntoMap::class)
                    .addAnnotation(
                        AnnotationSpec.builder(BindingKey::class)
                            .addMember(CodeBlock.of("%N::class", clazz.shortName))
                            .build()
                    )
                    .addParameter("injector", bindingTypeImpl)
                    .returns( AnvilInjector::class.asClassName().parameterizedBy(STAR) )
                    .build()
            )
            .build()
    }
}