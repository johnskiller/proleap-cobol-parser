/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.parser.metamodel.procedure.evaluate.impl;

import io.proleap.cobol.Cobol85Parser.EvaluateAlsoSelectContext;
import io.proleap.cobol.parser.metamodel.ProgramUnit;
import io.proleap.cobol.parser.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.parser.metamodel.procedure.evaluate.AlsoSelect;
import io.proleap.cobol.parser.metamodel.procedure.evaluate.Select;

public class AlsoSelectImpl extends CobolDivisionElementImpl implements AlsoSelect {

	protected final EvaluateAlsoSelectContext ctx;

	protected Select select;

	public AlsoSelectImpl(final ProgramUnit programUnit, final EvaluateAlsoSelectContext ctx) {
		super(programUnit, ctx);

		this.ctx = ctx;
	}

	@Override
	public Select getSelect() {
		return select;
	}

	@Override
	public void setSelect(final Select select) {
		this.select = select;
	}

}