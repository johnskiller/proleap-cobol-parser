/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.parser.metamodel.call.impl;

import org.antlr.v4.runtime.tree.ParseTree;

import io.proleap.cobol.parser.metamodel.CobolDivision;
import io.proleap.cobol.parser.metamodel.ProgramUnit;
import io.proleap.cobol.parser.metamodel.call.ProcedureCall;
import io.proleap.cobol.parser.metamodel.procedure.Paragraph;

public class ProcedureCallImpl extends CallImpl implements ProcedureCall {

	protected Paragraph paragraph;

	public ProcedureCallImpl(final String name, final Paragraph paragraph, final ProgramUnit programUnit,
			final CobolDivision scope, final ParseTree ctx) {
		super(name, programUnit, scope, ctx);

		this.paragraph = paragraph;
	}

	@Override
	public CallType getCallType() {
		return CallType.ProcedureCall;
	}

	@Override
	public Paragraph getParagraph() {
		return paragraph;
	}

	@Override
	public String toString() {
		return super.toString() + ", paragraph=[" + paragraph + "]";
	}
}