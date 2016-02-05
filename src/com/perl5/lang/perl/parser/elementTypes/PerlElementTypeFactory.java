/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.perl.parser.elementTypes;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.perl.idea.stubs.PerlStubElementTypes;
import com.perl5.lang.perl.psi.impl.*;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by hurricup on 25.05.2015.
 */
public class PerlElementTypeFactory
{
	protected final static Set<String> STRING_TOKENS = new THashSet<String>(Arrays.asList(
			"STRING_PLUS",
			"STRING_IDENTIFIER",
			"STRING_PACKAGE",
			"STRING_CONTENT"
	));

	public static IElementType getTokenType(@NotNull String name)
	{
		if (STRING_TOKENS.contains(name))
			return new PerlStringContentTokenType(name);
		if (name.equals("HEREDOC_END"))
			return new PerlTokenTypeEx(name)
			{
				@NotNull
				@Override
				public ASTNode createLeafNode(CharSequence leafText)
				{
					return new PerlHeredocTerminatorElementImpl(this, leafText);
				}
			};
		if (name.equals("VARIABLE_NAME"))
			return new PerlTokenTypeEx(name)
			{
				@NotNull
				@Override
				public ASTNode createLeafNode(CharSequence leafText)
				{
					return new PerlVariableNameElementImpl(this, leafText);
				}
			};
		if (name.equals("SUB"))
			return new PerlTokenTypeEx(name)
			{
				@NotNull
				@Override
				public ASTNode createLeafNode(CharSequence leafText)
				{
					return new PerlSubNameElementImpl(this, leafText);
				}
			};
		if (name.equals("PACKAGE"))
			return new PerlTokenTypeEx(name)
			{
				@NotNull
				@Override
				public ASTNode createLeafNode(CharSequence leafText)
				{
					return new PerlNamespaceElementImpl(this, leafText);
				}
			};
		if (name.equals("VERSION_ELEMENT"))
			return new PerlTokenTypeEx(name)
			{
				@NotNull
				@Override
				public ASTNode createLeafNode(CharSequence leafText)
				{
					return new PerlVersionElementImpl(this, leafText);
				}
			};
		if (name.equals("POD"))
			return new PerlTokenTypeEx(name)
			{
				@NotNull
				@Override
				public ASTNode createLeafNode(CharSequence leafText)
				{
					return new PsiCommentImpl(this, leafText);
				}
			};

		// fixme we should create self-convertable element types
		if (name.equals("HEREDOC_QQ") || name.equals("HEREDOC_QX") || name.equals("HEREDOC"))
			return new PerlHeredocElementType(name);
		if (name.equals("PARSABLE_STRING_USE_VARS"))
			return new PerlQQStringElementType(name);
		return new PerlTokenType(name);
	}

	public static IElementType getElementType(@NotNull String name)
	{
		if (name.equals("SUB_DEFINITION"))
			return PerlStubElementTypes.SUB_DEFINITION;
		else if (name.equals("METHOD_DEFINITION"))
			return PerlStubElementTypes.METHOD_DEFINITION;
		else if (name.equals("FUNC_DEFINITION"))
			return PerlStubElementTypes.FUNC_DEFINITION;
		else if (name.equals("SUB_DECLARATION"))
			return PerlStubElementTypes.SUB_DECLARATION;
		else if (name.equals("SUB_DECLARATION"))
			return PerlStubElementTypes.SUB_DECLARATION;
		else if (name.equals("GLOB_VARIABLE"))
			return PerlStubElementTypes.PERL_GLOB;
		else if (name.equals("NAMESPACE_DEFINITION"))
			return PerlStubElementTypes.PERL_NAMESPACE;
		else if (name.equals("VARIABLE_DECLARATION_WRAPPER"))
			return PerlStubElementTypes.PERL_VARIABLE_DECLARATION_WRAPPER;
		else if (name.equals("CONSTANT_NAME"))
			return PerlStubElementTypes.PERL_CONSTANT;

		else if (name.equals("USE_STATEMENT"))
			return PerlStubElementTypes.PERL_USE_STATEMENT;
		else if (name.equals("NO_STATEMENT"))
			return PerlStubElementTypes.PERL_NO_STATEMENT;
		else if (name.equals("DO_EXPR"))
			return PerlStubElementTypes.PERL_DO_EXPR;
		else if (name.equals("REQUIRE_EXPR"))
			return PerlStubElementTypes.PERL_REQUIRE_EXPR;


		// below is auto-generated from PerlElementTypes
		if (name.equals("ADD_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAddExprImpl(node);
				}
			};

		if (name.equals("AND_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAndExprImpl(node);
				}
			};

		if (name.equals("ANNOTATION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationImpl(node);
				}
			};

		if (name.equals("ANNOTATION_ABSTRACT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationAbstractImpl(node);
				}
			};

		if (name.equals("ANNOTATION_DEPRECATED"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationDeprecatedImpl(node);
				}
			};

		if (name.equals("ANNOTATION_INCOMPLETE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationIncompleteImpl(node);
				}
			};

		if (name.equals("ANNOTATION_METHOD"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationMethodImpl(node);
				}
			};

		if (name.equals("ANNOTATION_OVERRIDE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationOverrideImpl(node);
				}
			};

		if (name.equals("ANNOTATION_RETURNS_ARRAYREF"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationReturnsArrayrefImpl(node);
				}
			};

		if (name.equals("ANNOTATION_RETURNS_HASHREF"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationReturnsHashrefImpl(node);
				}
			};

		if (name.equals("ANNOTATION_RETURNS_REF"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationReturnsRefImpl(node);
				}
			};

		if (name.equals("ANNOTATION_UNKNOWN"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnnotationUnknownImpl(node);
				}
			};

		if (name.equals("ANON_ARRAY"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnonArrayImpl(node);
				}
			};

		if (name.equals("ANON_ARRAY_ELEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnonArrayElementImpl(node);
				}
			};

		if (name.equals("ANON_HASH"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnonHashImpl(node);
				}
			};

		if (name.equals("ANON_SUB"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAnonSubImpl(node);
				}
			};

		if (name.equals("ARRAY_ARRAY_SLICE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlArrayArraySliceImpl(node);
				}
			};

		if (name.equals("ARRAY_CAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlArrayCastExprImpl(node);
				}
			};

		if (name.equals("ARRAY_HASH_SLICE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlArrayHashSliceImpl(node);
				}
			};

		if (name.equals("ARRAY_INDEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlArrayIndexImpl(node);
				}
			};

		if (name.equals("ARRAY_INDEX_VARIABLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlArrayIndexVariableImpl(node);
				}
			};

		if (name.equals("ARRAY_VARIABLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlArrayVariableImpl(node);
				}
			};

		if (name.equals("ASSIGN_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAssignExprImpl(node);
				}
			};

		if (name.equals("ATTRIBUTE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlAttributeImpl(node);
				}
			};

		if (name.equals("BITWISE_AND_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlBitwiseAndExprImpl(node);
				}
			};

		if (name.equals("BITWISE_OR_XOR_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlBitwiseOrXorExprImpl(node);
				}
			};

		if (name.equals("BLOCK"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlBlockImpl(node);
				}
			};

		if (name.equals("CALL_ARGUMENTS"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlCallArgumentsImpl(node);
				}
			};

		if (name.equals("CODE_CAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlCodeCastExprImpl(node);
				}
			};

		if (name.equals("CODE_VARIABLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlCodeVariableImpl(node);
				}
			};

		if (name.equals("COMMA_SEQUENCE_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlCommaSequenceExprImpl(node);
				}
			};

		if (name.equals("COMPARE_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlCompareExprImpl(node);
				}
			};

		if (name.equals("COMPILE_REGEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlCompileRegexImpl(node);
				}
			};

		if (name.equals("CONDITIONAL_BLOCK"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConditionalBlockImpl(node);
				}
			};

		if (name.equals("CONDITIONAL_BLOCK_WHILE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConditionalBlockWhileImpl(node);
				}
			};

		if (name.equals("CONDITION_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConditionStatementImpl(node);
				}
			};

		if (name.equals("CONDITION_STATEMENT_WHILE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConditionStatementWhileImpl(node);
				}
			};

		if (name.equals("CONSTANTS_BLOCK"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConstantsBlockImpl(node);
				}
			};

		if (name.equals("CONSTANT_DEFINITION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConstantDefinitionImpl(node);
				}
			};

		if (name.equals("CONSTANT_NAME"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlConstantNameImpl(node);
				}
			};

		if (name.equals("CONTINUE_BLOCK"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlContinueBlockImpl(node);
				}
			};

		if (name.equals("DEFAULT_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlDefaultCompoundImpl(node);
				}
			};

		if (name.equals("DEREF_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlDerefExprImpl(node);
				}
			};

		if (name.equals("DO_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlDoExprImpl(node);
				}
			};

		if (name.equals("EQUAL_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlEqualExprImpl(node);
				}
			};

		if (name.equals("EVAL_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlEvalExprImpl(node);
				}
			};

		if (name.equals("EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlExprImpl(node);
				}
			};

		if (name.equals("FILE_READ_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlFileReadExprImpl(node);
				}
			};

		if (name.equals("FILE_READ_FORCED_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlFileReadForcedExprImpl(node);
				}
			};

		if (name.equals("FLIPFLOP_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlFlipflopExprImpl(node);
				}
			};

		if (name.equals("FOREACH_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForeachCompoundImpl(node);
				}
			};

		if (name.equals("FOREACH_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForeachStatementModifierImpl(node);
				}
			};

		if (name.equals("FORMAT_DEFINITION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlFormatDefinitionImpl(node);
				}
			};

		if (name.equals("FOR_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForCompoundImpl(node);
				}
			};

		if (name.equals("FOR_ITERATOR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForIteratorImpl(node);
				}
			};

		if (name.equals("FOR_ITERATOR_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForIteratorStatementImpl(node);
				}
			};

		if (name.equals("FOR_LIST_EPXR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForListEpxrImpl(node);
				}
			};

		if (name.equals("FOR_LIST_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForListStatementImpl(node);
				}
			};

		if (name.equals("FOR_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlForStatementModifierImpl(node);
				}
			};

		if (name.equals("FUNC_DEFINITION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlFuncDefinitionImpl(node);
				}
			};

		if (name.equals("FUNC_SIGNATURE_CONTENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlFuncSignatureContentImpl(node);
				}
			};

		if (name.equals("GIVEN_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlGivenCompoundImpl(node);
				}
			};

		if (name.equals("GLOB_CAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlGlobCastExprImpl(node);
				}
			};

		if (name.equals("GLOB_SLOT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlGlobSlotImpl(node);
				}
			};

		if (name.equals("GLOB_VARIABLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlGlobVariableImpl(node);
				}
			};

		if (name.equals("GOTO_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlGotoExprImpl(node);
				}
			};

		if (name.equals("GREP_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlGrepExprImpl(node);
				}
			};

		if (name.equals("HASH_CAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlHashCastExprImpl(node);
				}
			};

		if (name.equals("HASH_INDEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlHashIndexImpl(node);
				}
			};

		if (name.equals("HASH_VARIABLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlHashVariableImpl(node);
				}
			};

		if (name.equals("HEREDOC_OPENER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlHeredocOpenerImpl(node);
				}
			};

		if (name.equals("IF_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlIfCompoundImpl(node);
				}
			};

		if (name.equals("IF_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlIfStatementModifierImpl(node);
				}
			};

		if (name.equals("LABEL_DECLARATION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlLabelDeclarationImpl(node);
				}
			};

		if (name.equals("LAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlLastExprImpl(node);
				}
			};

		if (name.equals("LP_AND_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlLpAndExprImpl(node);
				}
			};

		if (name.equals("LP_NOT_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlLpNotExprImpl(node);
				}
			};

		if (name.equals("LP_OR_XOR_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlLpOrXorExprImpl(node);
				}
			};

		if (name.equals("MAP_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMapExprImpl(node);
				}
			};

		if (name.equals("MATCH_REGEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMatchRegexImpl(node);
				}
			};

		if (name.equals("METHOD"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMethodImpl(node);
				}
			};

		if (name.equals("METHOD_DEFINITION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMethodDefinitionImpl(node);
				}
			};

		if (name.equals("METHOD_SIGNATURE_CONTENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMethodSignatureContentImpl(node);
				}
			};

		if (name.equals("METHOD_SIGNATURE_INVOCANT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMethodSignatureInvocantImpl(node);
				}
			};

		if (name.equals("MUL_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlMulExprImpl(node);
				}
			};

		if (name.equals("NAMED_BLOCK"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNamedBlockImpl(node);
				}
			};

		if (name.equals("NAMED_LIST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNamedListExprImpl(node);
				}
			};

		if (name.equals("NAMED_UNARY_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNamedUnaryExprImpl(node);
				}
			};

		if (name.equals("NAMESPACE_CONTENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNamespaceContentImpl(node);
				}
			};

		if (name.equals("NAMESPACE_DEFINITION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNamespaceDefinitionImpl(node);
				}
			};

		if (name.equals("NAMESPACE_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNamespaceExprImpl(node);
				}
			};

		if (name.equals("NESTED_CALL"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNestedCallImpl(node);
				}
			};

		if (name.equals("NESTED_CALL_ARGUMENTS"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNestedCallArgumentsImpl(node);
				}
			};

		if (name.equals("NEXT_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNextExprImpl(node);
				}
			};

		if (name.equals("NO_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNoStatementImpl(node);
				}
			};

		if (name.equals("NUMBER_CONSTANT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNumberConstantImpl(node);
				}
			};

		if (name.equals("NYI_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlNyiStatementImpl(node);
				}
			};

		if (name.equals("OR_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlOrExprImpl(node);
				}
			};

		if (name.equals("PARENTHESISED_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlParenthesisedExprImpl(node);
				}
			};

		if (name.equals("PERL_HANDLE_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPerlHandleExprImpl(node);
				}
			};

		if (name.equals("PERL_REGEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPerlRegexImpl(node);
				}
			};

		if (name.equals("PERL_REGEX_EX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPerlRegexExImpl(node);
				}
			};

		if (name.equals("PERL_REGEX_MODIFIERS"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPerlRegexModifiersImpl(node);
				}
			};

		if (name.equals("POW_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPowExprImpl(node);
				}
			};

		if (name.equals("PREFIX_MINUS_AS_STRING_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPrefixMinusAsStringExprImpl(node);
				}
			};

		if (name.equals("PREFIX_UNARY_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPrefixUnaryExprImpl(node);
				}
			};

		if (name.equals("PREF_MM_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPrefMmExprImpl(node);
				}
			};

		if (name.equals("PREF_PP_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPrefPpExprImpl(node);
				}
			};

		if (name.equals("PRINT_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPrintExprImpl(node);
				}
			};

		if (name.equals("PRINT_HANDLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlPrintHandleImpl(node);
				}
			};

		if (name.equals("READ_HANDLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlReadHandleImpl(node);
				}
			};

		if (name.equals("REDO_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlRedoExprImpl(node);
				}
			};

		if (name.equals("REF_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlRefExprImpl(node);
				}
			};

		if (name.equals("REGEX_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlRegexExprImpl(node);
				}
			};

		if (name.equals("REPLACEMENT_REGEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlReplacementRegexImpl(node);
				}
			};

		if (name.equals("REQUIRE_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlRequireExprImpl(node);
				}
			};

		if (name.equals("RETURN_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlReturnExprImpl(node);
				}
			};

		if (name.equals("SCALAR_ARRAY_ELEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlScalarArrayElementImpl(node);
				}
			};

		if (name.equals("SCALAR_CALL"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlScalarCallImpl(node);
				}
			};

		if (name.equals("SCALAR_CAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlScalarCastExprImpl(node);
				}
			};

		if (name.equals("SCALAR_HASH_ELEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlScalarHashElementImpl(node);
				}
			};

		if (name.equals("SCALAR_INDEX_CAST_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlScalarIndexCastExprImpl(node);
				}
			};

		if (name.equals("SCALAR_VARIABLE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlScalarVariableImpl(node);
				}
			};

		if (name.equals("SHIFT_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlShiftExprImpl(node);
				}
			};

		if (name.equals("SORT_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSortExprImpl(node);
				}
			};

		if (name.equals("STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStatementImpl(node);
				}
			};

		if (name.equals("STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStatementModifierImpl(node);
				}
			};

		if (name.equals("STRING_BARE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStringBareImpl(node);
				}
			};

		if (name.equals("STRING_DQ"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStringDqImpl(node);
				}
			};

		if (name.equals("STRING_LIST"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStringListImpl(node);
				}
			};

		if (name.equals("STRING_SQ"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStringSqImpl(node);
				}
			};

		if (name.equals("STRING_XQ"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlStringXqImpl(node);
				}
			};

		if (name.equals("SUB_CALL_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSubCallExprImpl(node);
				}
			};

		if (name.equals("SUB_DECLARATION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSubDeclarationImpl(node);
				}
			};

		if (name.equals("SUB_DEFINITION"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSubDefinitionImpl(node);
				}
			};

		if (name.equals("SUB_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSubExprImpl(node);
				}
			};

		if (name.equals("SUB_SIGNATURE_CONTENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSubSignatureContentImpl(node);
				}
			};

		if (name.equals("SUB_SIGNATURE_ELEMENT_IGNORE"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSubSignatureElementIgnoreImpl(node);
				}
			};

		if (name.equals("SUFF_PP_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlSuffPpExprImpl(node);
				}
			};

		if (name.equals("TAG_SCALAR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTagScalarImpl(node);
				}
			};

		if (name.equals("TERM_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTermExprImpl(node);
				}
			};

		if (name.equals("TRENAR_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTrenarExprImpl(node);
				}
			};

		if (name.equals("TR_MODIFIERS"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTrModifiersImpl(node);
				}
			};

		if (name.equals("TR_REGEX"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTrRegexImpl(node);
				}
			};

		if (name.equals("TR_REPLACEMENTLIST"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTrReplacementlistImpl(node);
				}
			};

		if (name.equals("TR_SEARCHLIST"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlTrSearchlistImpl(node);
				}
			};

		if (name.equals("UNCONDITIONAL_BLOCK"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUnconditionalBlockImpl(node);
				}
			};

		if (name.equals("UNDEF_EXPR"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUndefExprImpl(node);
				}
			};

		if (name.equals("UNLESS_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUnlessCompoundImpl(node);
				}
			};

		if (name.equals("UNLESS_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUnlessStatementModifierImpl(node);
				}
			};

		if (name.equals("UNTIL_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUntilCompoundImpl(node);
				}
			};

		if (name.equals("UNTIL_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUntilStatementModifierImpl(node);
				}
			};

		if (name.equals("USE_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUseStatementImpl(node);
				}
			};

		if (name.equals("USE_STATEMENT_CONSTANT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUseStatementConstantImpl(node);
				}
			};

		if (name.equals("USE_VARS_STATEMENT"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlUseVarsStatementImpl(node);
				}
			};

		if (name.equals("VARIABLE_DECLARATION_GLOBAL"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlVariableDeclarationGlobalImpl(node);
				}
			};

		if (name.equals("VARIABLE_DECLARATION_LEXICAL"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlVariableDeclarationLexicalImpl(node);
				}
			};

		if (name.equals("VARIABLE_DECLARATION_LOCAL"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlVariableDeclarationLocalImpl(node);
				}
			};

		if (name.equals("VARIABLE_DECLARATION_WRAPPER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlVariableDeclarationWrapperImpl(node);
				}
			};

		if (name.equals("WHEN_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlWhenCompoundImpl(node);
				}
			};

		if (name.equals("WHEN_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlWhenStatementModifierImpl(node);
				}
			};

		if (name.equals("WHILE_COMPOUND"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlWhileCompoundImpl(node);
				}
			};

		if (name.equals("WHILE_STATEMENT_MODIFIER"))
			return new PerlElementTypeEx(name)
			{
				@NotNull
				@Override
				public PsiElement getPsiElement(@NotNull ASTNode node)
				{
					return new PsiPerlWhileStatementModifierImpl(node);
				}
			};


		return new PerlElementType(name);
	}
}