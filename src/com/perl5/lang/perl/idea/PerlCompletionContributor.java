/*
 * Copyright 2015 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.idea;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.psi.impl.*;
import com.perl5.lang.perl.util.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * Created by hurricup on 25.04.2015.
 *
 */
public class PerlCompletionContributor extends CompletionContributor implements PerlElementTypes
{

    private void fillScalarCompletions(@NotNull CompletionParameters parameters,
                                       ProcessingContext context,
                                       @NotNull CompletionResultSet resultSet)
    {

        for( String name: PerlScalarUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
        }
        for( String name: PerlArrayUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1) + "[]"));
        }
        for( String name: PerlHashUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1) + "{}"));
        }
    }
    private void fillArrayCompletions(@NotNull CompletionParameters parameters,
                                       ProcessingContext context,
                                       @NotNull CompletionResultSet resultSet)
    {
        // built in arrays
        for( String name: PerlArrayUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
        }
        for( String name: PerlHashUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1) + "{}"));
        }

    }
    private void fillHashCompletions(@NotNull CompletionParameters parameters,
                                       ProcessingContext context,
                                       @NotNull CompletionResultSet resultSet)
    {
        // built-in hashes
        for( String name: PerlHashUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
        }

    }
    private void fillGlobCompletions(@NotNull CompletionParameters parameters,
                                       ProcessingContext context,
                                       @NotNull CompletionResultSet resultSet)
    {
        // built-in globs
        for( String name: PerlGlobUtil.BUILT_IN )
        {
            resultSet.addElement(LookupElementBuilder.create(name.substring(1)));
        }
    }


	public PerlCompletionContributor()
    {

        // router built-in
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(PerlElementTypes.PERL_VARIABLE_NAME).withLanguage(PerlLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>()
                {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet)
                    {

                        PsiElement variableName = parameters.getPosition().getParent();
                        assert variableName != null;
                        PsiElement variable = variableName.getParent();

                        if (variable instanceof PerlPerlScalar)
                            fillScalarCompletions(parameters, context, resultSet);
                        else if (variable instanceof PerlPerlArray)
                            fillArrayCompletions(parameters, context, resultSet);
                        else if (variable instanceof PerlPerlHash)
                            fillHashCompletions(parameters, context, resultSet);
                        else if (variable instanceof PerlPerlGlob)
                            fillGlobCompletions(parameters, context, resultSet);
                    }
                }
        );

        // Variables
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(PerlElementTypes.PERL_VARIABLE_NAME).withLanguage(PerlLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>()
                {
                    public void addCompletions(@NotNull final CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull final CompletionResultSet resultSet)
                    {

                        final PsiElement variableName = parameters.getPosition().getParent();
                        assert variableName != null;

                        if( variableName instanceof PerlVariableName )
                        {

                            final PsiElement perlVariable = variableName.getParent();

                            if (perlVariable instanceof PerlPerlScalar)
                                ApplicationManager.getApplication().runReadAction(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        String currentText = variableName.getText();

                                        Collection<PerlVariable> declaredVariables = PerlUtil.findDeclaredLexicalVariables(perlVariable);

                                        // locals, todo we should limit with lexicaly visible
                                        for (PerlVariable variable : declaredVariables)
                                        {
                                            if (variable instanceof PerlPerlScalar)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName()));
                                            } else if (variable instanceof PerlPerlArray)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName() + "[]"));

                                            } else if (variable instanceof PerlPerlHash)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName() + "{}"));

                                            }
                                        }

                                        // global scalars
                                        for( String name: PerlScalarUtil.listDefinedGlobalScalars(variableName.getProject()))
                                        {
                                            resultSet.addElement(LookupElementBuilder.create(name));
                                        }
                                        // global arrays
                                        for( String name: PerlArrayUtil.listDefinedGlobalArrays(variableName.getProject()))
                                        {
                                            resultSet.addElement(LookupElementBuilder.create(name + "[]"));
                                        }
                                        // global hashes
                                        for( String name: PerlHashUtil.listDefinedGlobalHahses(variableName.getProject()))
                                        {
                                            resultSet.addElement(LookupElementBuilder.create(name + "{}"));
                                        }

                                    }
                                });
                            else if (perlVariable instanceof PerlPerlArray)
                                ApplicationManager.getApplication().runReadAction(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        String currentText = variableName.getText();

                                        Collection<PerlVariable> declaredVariables = PerlUtil.findDeclaredLexicalVariables(perlVariable);
                                        boolean useScalars = ((PerlPerlArray) perlVariable).getScalarSigils() != null;

                                        for (PerlVariable variable : declaredVariables)
                                        {
                                            if (variable instanceof PerlPerlScalar && useScalars)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName()));
                                            } else if (variable instanceof PerlPerlArray)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName()));

                                            } else if (variable instanceof PerlPerlHash)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName() + "{}"));
                                            }
                                        }
                                        // global scalars
                                        if( useScalars )
                                        {
                                            for (String name : PerlScalarUtil.listDefinedGlobalScalars(variableName.getProject()))
                                            {
                                                resultSet.addElement(LookupElementBuilder.create(name));
                                            }
                                        }
                                        // global arrays
                                        for( String name: PerlArrayUtil.listDefinedGlobalArrays(variableName.getProject()))
                                        {
                                            resultSet.addElement(LookupElementBuilder.create(name));
                                        }
                                        // global hashes
                                        for( String name: PerlHashUtil.listDefinedGlobalHahses(variableName.getProject()))
                                        {
                                            resultSet.addElement(LookupElementBuilder.create(name + "{}"));
                                        }
                                    }
                                });
                            else if (perlVariable instanceof PerlPerlHash)
                                ApplicationManager.getApplication().runReadAction(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        String currentText = variableName.getText();

                                        Collection<PerlVariable> declaredVariables = PerlUtil.findDeclaredLexicalVariables(perlVariable);
                                        boolean useScalars = ((PerlPerlHash) perlVariable).getScalarSigils() != null;

                                        for (PerlVariable variable : declaredVariables)
                                        {
                                            if (variable instanceof PerlPerlScalar && useScalars)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName()));
                                            } else if (variable instanceof PerlPerlHash)
                                            {
                                                PerlVariableName variableName = variable.getVariableName();
                                                if (variableName != null && variableName.getName() != null)
                                                    resultSet.addElement(LookupElementBuilder.create(variableName.getName()));

                                            }
                                        }

                                        // global scalars
                                        if( useScalars )
                                        {
                                            for (String name : PerlScalarUtil.listDefinedGlobalScalars(variableName.getProject()))
                                            {
                                                resultSet.addElement(LookupElementBuilder.create(name));
                                            }
                                        }
                                        // global hashes
                                        for( String name: PerlHashUtil.listDefinedGlobalHahses(variableName.getProject()))
                                        {
                                            resultSet.addElement(LookupElementBuilder.create(name));
                                        }

                                    }
                                });
                        }
                    }
                }
        );

        // project subs
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(PerlElementTypes.PERL_FUNCTION).withLanguage(PerlLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>()
                {
                    public void addCompletions(@NotNull final CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull final CompletionResultSet resultSet)
                    {

                        String packageName = null;
                        PsiElement parent = parameters.getPosition().getParent();
                        if( parent != null && parent instanceof PerlPackagedElement)
                            packageName = ((PerlPackagedElement) parent).getPackageName();

                        final String finalPackageName = packageName == null ? null: packageName + "::";

                        final Project project = parameters.getPosition().getProject();

                        ApplicationManager.getApplication().runReadAction(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Collection<String> definedSubs = PerlFunctionUtil.getDefinedSubsNames(project);
                                definedSubs.addAll(PerlGlobUtil.getDefinedGlobsNames(project));

                                for (String subname : definedSubs )
                                {
                                    if( finalPackageName != null && subname.startsWith(finalPackageName) )
                                        subname = subname.substring(finalPackageName.length());
                                    LookupElementBuilder elementBuilder = LookupElementBuilder.create(subname);

                                    if( subname.contains("::"))
                                    {
                                        resultSet.addElement(elementBuilder.withLookupString(subname.substring(subname.lastIndexOf("::")+2)));
                                    }
                                    else
                                    {
                                        resultSet.addElement(elementBuilder);
                                    }

                                }
                            }
                        });
                    }
                }
        );

        // used packages
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(PerlElementTypes.PERL_FUNCTION).withLanguage(PerlLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>()
                {
                    public void addCompletions(@NotNull final CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull final CompletionResultSet resultSet)
                    {

                        ApplicationManager.getApplication().runReadAction(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                PsiFile file = parameters.getOriginalFile();

                                for (PerlUseStatementImpl use : PsiTreeUtil.findChildrenOfType(file, PerlUseStatementImpl.class))
                                {
                                    PerlNamespace namespace = use.getNamespace();

                                    if (namespace != null)
                                        resultSet.addElement(LookupElementBuilder.create(namespace.getText()));
                                }

                                for (PerlRequireTermImpl use : PsiTreeUtil.findChildrenOfType(file, PerlRequireTermImpl.class))
                                {
                                    PerlNamespace namespace = use.getNamespace();

                                    if (namespace != null)
                                        resultSet.addElement(LookupElementBuilder.create(namespace.getText()));
                                }
                            }
                        });
                    }
                }
        );

        // built in functions
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(PerlElementTypes.PERL_FUNCTION).withLanguage(PerlLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>()
                {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet)
                    {

                        for (String functionName : PerlFunctionUtil.BUILT_IN)
                        {
                            resultSet.addElement(LookupElementBuilder.create(functionName));
                        }
                    }
                }
        );
    }


	@Override
	public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result)
	{
		super.fillCompletionVariants(parameters, result);
	}
}
