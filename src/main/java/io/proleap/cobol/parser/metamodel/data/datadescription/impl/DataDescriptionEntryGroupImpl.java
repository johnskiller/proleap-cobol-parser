/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.parser.metamodel.data.datadescription.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.proleap.cobol.Cobol85Parser.DataAlignedClauseContext;
import io.proleap.cobol.Cobol85Parser.DataBlankWhenZeroClauseContext;
import io.proleap.cobol.Cobol85Parser.DataCommonOwnLocalClauseContext;
import io.proleap.cobol.Cobol85Parser.DataDescriptionEntryFormat1Context;
import io.proleap.cobol.Cobol85Parser.DataExternalClauseContext;
import io.proleap.cobol.Cobol85Parser.DataGlobalClauseContext;
import io.proleap.cobol.Cobol85Parser.DataIntegerStringClauseContext;
import io.proleap.cobol.Cobol85Parser.DataJustifiedClauseContext;
import io.proleap.cobol.Cobol85Parser.DataOccursClauseContext;
import io.proleap.cobol.Cobol85Parser.DataOccursSortContext;
import io.proleap.cobol.Cobol85Parser.DataPictureClauseContext;
import io.proleap.cobol.Cobol85Parser.DataReceivedByClauseContext;
import io.proleap.cobol.Cobol85Parser.DataRecordAreaClauseContext;
import io.proleap.cobol.Cobol85Parser.DataRedefinesClauseContext;
import io.proleap.cobol.Cobol85Parser.DataSignClauseContext;
import io.proleap.cobol.Cobol85Parser.DataSynchronizedClauseContext;
import io.proleap.cobol.Cobol85Parser.DataThreadLocalClauseContext;
import io.proleap.cobol.Cobol85Parser.DataTypeClauseContext;
import io.proleap.cobol.Cobol85Parser.DataTypeDefClauseContext;
import io.proleap.cobol.Cobol85Parser.DataUsageClauseContext;
import io.proleap.cobol.Cobol85Parser.DataUsingClauseContext;
import io.proleap.cobol.Cobol85Parser.DataValueClauseContext;
import io.proleap.cobol.Cobol85Parser.DataValueIntervalContext;
import io.proleap.cobol.Cobol85Parser.DataWithLowerBoundsClauseContext;
import io.proleap.cobol.Cobol85Parser.IndexNameContext;
import io.proleap.cobol.Cobol85Parser.PictureStringContext;
import io.proleap.cobol.parser.metamodel.IntegerLiteral;
import io.proleap.cobol.parser.metamodel.ProgramUnit;
import io.proleap.cobol.parser.metamodel.data.datadescription.AlignedClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.BlankWhenZeroClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.CommonOwnLocalClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.DataDescriptionEntry;
import io.proleap.cobol.parser.metamodel.data.datadescription.DataDescriptionEntryGroup;
import io.proleap.cobol.parser.metamodel.data.datadescription.ExternalClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.GlobalClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.IntegerStringClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.JustifiedClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.OccursClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.PictureClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.ReceivedByClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.RecordAreaClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.RedefinesClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.SignClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.SynchronizedClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.ThreadLocalClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.TypeClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.TypeDefClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.UsageClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.UsingClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.ValueClause;
import io.proleap.cobol.parser.metamodel.data.datadescription.WithLowerBoundsClause;
import io.proleap.cobol.parser.metamodel.valuestmt.CallValueStmt;
import io.proleap.cobol.parser.metamodel.valuestmt.LiteralValueStmt;
import io.proleap.cobol.parser.metamodel.valuestmt.ValueStmt;

public class DataDescriptionEntryGroupImpl extends DataDescriptionEntryImpl implements DataDescriptionEntryGroup {

	private final static Logger LOG = LogManager.getLogger(DataDescriptionEntryGroupImpl.class);

	protected AlignedClause alignedClause;

	protected BlankWhenZeroClause blankWhenZeroClause;

	protected CommonOwnLocalClause commonOwnLocalClause;

	protected final DataDescriptionEntryFormat1Context ctx;

	protected List<DataDescriptionEntry> dataDescriptionEntries = new ArrayList<DataDescriptionEntry>();

	protected Map<String, DataDescriptionEntry> dataDescriptionEntriesSymbolTable = new HashMap<String, DataDescriptionEntry>();

	protected ExternalClause externalClause;

	protected GlobalClause globalClause;

	protected IntegerStringClause integerStringClause;

	protected JustifiedClause justifiedClause;

	protected List<OccursClause> occursClauses = new ArrayList<OccursClause>();

	protected PictureClause pictureClause;

	protected ReceivedByClause receivedByClause;

	protected RecordAreaClause recordAreaClause;

	protected RedefinesClause redefinesClause;

	protected SignClause signClause;

	protected SynchronizedClause synchronizedClause;

	protected ThreadLocalClause threadLocalClause;

	protected TypeClause typeClause;

	protected TypeDefClause typeDefClause;

	protected UsageClause usageClause;

	protected UsingClause usingClause;

	protected ValueClause valueClause;

	protected WithLowerBoundsClause withLowerBoundsClause;

	public DataDescriptionEntryGroupImpl(final String name, final ProgramUnit programUnit,
			final DataDescriptionEntryFormat1Context ctx) {
		super(name, programUnit, ctx);

		this.ctx = ctx;
	}

	@Override
	public AlignedClause addAlignedClause(final DataAlignedClauseContext ctx) {
		AlignedClause result = (AlignedClause) getASGElement(ctx);

		if (result == null) {
			result = new AlignedClauseImpl(programUnit, ctx);

			result.setAligned(true);

			alignedClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public BlankWhenZeroClause addBlankWhenZeroClause(final DataBlankWhenZeroClauseContext ctx) {
		BlankWhenZeroClause result = (BlankWhenZeroClause) getASGElement(ctx);

		if (result == null) {
			result = new BlankWhenZeroClauseImpl(programUnit, ctx);

			result.setBlankWhenZero(true);

			blankWhenZeroClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public CommonOwnLocalClause addCommonOwnLocalClause(final DataCommonOwnLocalClauseContext ctx) {
		CommonOwnLocalClause result = (CommonOwnLocalClause) getASGElement(ctx);

		if (result == null) {
			result = new CommonOwnLocalClauseImpl(programUnit, ctx);

			/*
			 * invariance
			 */
			final CommonOwnLocalClause.Invariance invariance;

			if (ctx.COMMON() != null) {
				invariance = CommonOwnLocalClause.Invariance.Common;
			} else if (ctx.OWN() != null) {
				invariance = CommonOwnLocalClause.Invariance.Own;
			} else if (ctx.LOCAL() != null) {
				invariance = CommonOwnLocalClause.Invariance.Local;
			} else {
				invariance = null;
			}

			result.setInvariance(invariance);

			commonOwnLocalClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public void addDataDescriptionEntry(final DataDescriptionEntry dataDescriptionEntry) {
		final String name = dataDescriptionEntry.getName();

		dataDescriptionEntries.add(dataDescriptionEntry);
		dataDescriptionEntriesSymbolTable.put(name, dataDescriptionEntry);
	}

	@Override
	public ExternalClause addExternalClause(final DataExternalClauseContext ctx) {
		ExternalClause result = (ExternalClause) getASGElement(ctx);

		if (result == null) {
			result = new ExternalClauseImpl(programUnit, ctx);

			result.setExternal(true);

			/*
			 * by literal
			 */
			if (ctx.literal() != null) {
				final LiteralValueStmt byLiteralValueStmt = createLiteralValueStmt(ctx.literal());
				result.setByLiteralValueStmt(byLiteralValueStmt);
			}

			externalClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public GlobalClause addGlobalClause(final DataGlobalClauseContext ctx) {
		GlobalClause result = (GlobalClause) getASGElement(ctx);

		if (result == null) {
			result = new GlobalClauseImpl(programUnit, ctx);

			result.setGlobal(true);

			globalClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public IntegerStringClause addIntegerStringClause(final DataIntegerStringClauseContext ctx) {
		IntegerStringClause result = (IntegerStringClause) getASGElement(ctx);

		if (result == null) {
			result = new IntegerStringClauseImpl(programUnit, ctx);

			final IntegerStringClause.PrimitiveType primitiveType;

			if (ctx.INTEGER() != null) {
				primitiveType = IntegerStringClause.PrimitiveType.Integer;
			} else if (ctx.STRING() != null) {
				primitiveType = IntegerStringClause.PrimitiveType.String;
			} else {
				primitiveType = null;
			}

			result.setPrimitiveType(primitiveType);

			integerStringClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public JustifiedClause addJustifiedClause(final DataJustifiedClauseContext ctx) {
		JustifiedClause result = (JustifiedClause) getASGElement(ctx);

		if (result == null) {
			result = new JustifiedClauseImpl(programUnit, ctx);

			/*
			 * justified
			 */
			final JustifiedClause.Justified justified;

			if (ctx.RIGHT() != null) {
				justified = JustifiedClause.Justified.JustifiedRight;
			} else {
				justified = JustifiedClause.Justified.Justified;
			}

			result.setJustified(justified);

			justifiedClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public OccursClause addOccursClause(final DataOccursClauseContext ctx) {
		OccursClause result = (OccursClause) getASGElement(ctx);

		if (result == null) {
			result = new OccursClauseImpl(programUnit, ctx);

			/*
			 * from
			 */
			final IntegerLiteral from = createIntegerLiteral(ctx.integerLiteral());
			result.setFrom(from);

			/*
			 * to
			 */
			if (ctx.dataOccursTo() != null) {
				final IntegerLiteral to = createIntegerLiteral(ctx.dataOccursTo().integerLiteral());
				result.setTo(to);
			}

			/*
			 * depending on
			 */
			if (ctx.qualifiedDataName() != null) {
				final CallValueStmt qualifiedDataNameValueStmt = createCallValueStmt(ctx.qualifiedDataName());
				result.setDependingOnValueStmt(qualifiedDataNameValueStmt);
			}

			/*
			 * data occurs sort
			 */
			for (final DataOccursSortContext dataOccursSortContext : ctx.dataOccursSort()) {
				result.addOccursSort(dataOccursSortContext);
			}

			/*
			 * index names
			 */
			for (final IndexNameContext indexNameContext : ctx.indexName()) {
				result.addIndexNameValueStmt(indexNameContext);
			}

			occursClauses.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public PictureClause addPictureClause(final DataPictureClauseContext ctx) {
		PictureClause result = (PictureClause) getASGElement(ctx);

		if (result == null) {
			result = new PictureClauseImpl(programUnit, ctx);

			final PictureStringContext pictureString = ctx.pictureString();
			result.setPictureString(pictureString.getText());

			pictureClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ReceivedByClause addReceivedByClause(final DataReceivedByClauseContext ctx) {
		ReceivedByClause result = (ReceivedByClause) getASGElement(ctx);

		if (result == null) {
			result = new ReceivedByClauseImpl(programUnit, ctx);

			/*
			 * received by
			 */
			final ReceivedByClause.ReceivedBy receivedBy;

			if (ctx.CONTENT() != null) {
				receivedBy = ReceivedByClause.ReceivedBy.Content;
			} else if (ctx.REFERENCE() != null) {
				receivedBy = ReceivedByClause.ReceivedBy.Reference;
			} else if (ctx.REF() != null) {
				receivedBy = ReceivedByClause.ReceivedBy.Reference;
			} else {
				receivedBy = null;
			}

			result.setReceivedBy(receivedBy);

			receivedByClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public RecordAreaClause addRecordAreaClause(final DataRecordAreaClauseContext ctx) {
		RecordAreaClause result = (RecordAreaClause) getASGElement(ctx);

		if (result == null) {
			result = new RecordAreaClauseImpl(programUnit, ctx);

			result.setRecordArea(true);

			recordAreaClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public RedefinesClause addRedefinesClause(final DataRedefinesClauseContext ctx) {
		RedefinesClause result = (RedefinesClause) getASGElement(ctx);

		if (result == null) {
			result = new RedefinesClauseImpl(programUnit, ctx);

			final ValueStmt redefinesValueStmt = createCallValueStmt(ctx.dataName());
			result.setRedefinesValueStmt(redefinesValueStmt);

			redefinesClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SignClause addSignClause(final DataSignClauseContext ctx) {
		SignClause result = (SignClause) getASGElement(ctx);

		if (result == null) {
			result = new SignClauseImpl(programUnit, ctx);

			/*
			 * type
			 */
			final SignClause.Type type;

			if (ctx.LEADING() != null) {
				type = SignClause.Type.Leading;
			} else if (ctx.TRAILING() != null) {
				type = SignClause.Type.Trailing;
			} else {
				type = null;
			}

			result.setType(type);

			/*
			 * separate
			 */
			if (ctx.SEPARATE() != null) {
				result.setSeparate(true);
			}

			signClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SynchronizedClause addSynchronizedClause(final DataSynchronizedClauseContext ctx) {
		SynchronizedClause result = (SynchronizedClause) getASGElement(ctx);

		if (result == null) {
			result = new SynchronizedClauseImpl(programUnit, ctx);

			final SynchronizedClause.Synchronized sync;

			if (ctx.LEFT() != null) {
				sync = SynchronizedClause.Synchronized.Left;
			} else if (ctx.RIGHT() != null) {
				sync = SynchronizedClause.Synchronized.Right;
			} else {
				sync = null;
			}

			result.setSynchronized(sync);

			synchronizedClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ThreadLocalClause addThreadLocalClause(final DataThreadLocalClauseContext ctx) {
		ThreadLocalClause result = (ThreadLocalClause) getASGElement(ctx);

		if (result == null) {
			result = new ThreadLocalClauseImpl(programUnit, ctx);

			result.setThreadLocal(true);

			threadLocalClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public TypeClause addTypeClause(final DataTypeClauseContext ctx) {
		TypeClause result = (TypeClause) getASGElement(ctx);

		if (result == null) {
			result = new TypeClauseImpl(programUnit, ctx);

			final TypeClause.TimeType timeType;

			if (ctx.SHORT_DATE() != null) {
				timeType = TypeClause.TimeType.ShortDate;
			} else if (ctx.LONG_DATE() != null) {
				timeType = TypeClause.TimeType.LongDate;
			} else if (ctx.NUMERIC_DATE() != null) {
				timeType = TypeClause.TimeType.NumericDate;
			} else if (ctx.NUMERIC_TIME() != null) {
				timeType = TypeClause.TimeType.NumericTime;
			} else if (ctx.LONG_TIME() != null) {
				timeType = TypeClause.TimeType.LongTime;
			} else {
				timeType = null;
			}

			result.setTimeType(timeType);

			typeClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public TypeDefClause addTypeDefClause(final DataTypeDefClauseContext ctx) {
		TypeDefClause result = (TypeDefClause) getASGElement(ctx);

		if (result == null) {
			result = new TypeDefClauseImpl(programUnit, ctx);

			result.setTypeDef(true);

			typeDefClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public UsageClause addUsageClause(final DataUsageClauseContext ctx) {
		UsageClause result = (UsageClause) getASGElement(ctx);

		if (result == null) {
			result = new UsageClauseImpl(programUnit, ctx);

			/*
			 * type
			 */
			final UsageClause.Type type;

			if (ctx.BINARY() != null) {
				type = UsageClause.Type.Binary;
			} else if (ctx.BIT() != null) {
				type = UsageClause.Type.Bit;
			} else if (ctx.COMP() != null || ctx.COMPUTATIONAL() != null) {
				type = UsageClause.Type.Comp;
			} else if (ctx.COMP_1() != null || ctx.COMPUTATIONAL_1() != null) {
				type = UsageClause.Type.Comp1;
			} else if (ctx.COMP_2() != null || ctx.COMPUTATIONAL_2() != null) {
				type = UsageClause.Type.Comp2;
			} else if (ctx.COMP_3() != null || ctx.COMPUTATIONAL_3() != null) {
				type = UsageClause.Type.Comp3;
			} else if (ctx.COMP_4() != null || ctx.COMPUTATIONAL_4() != null) {
				type = UsageClause.Type.Comp4;
			} else if (ctx.COMP_5() != null || ctx.COMPUTATIONAL_5() != null) {
				type = UsageClause.Type.Comp5;
			} else if (ctx.CONTROL_POINT() != null) {
				type = UsageClause.Type.ControlPoint;
			} else if (ctx.DATE() != null) {
				type = UsageClause.Type.Date;
			} else if (ctx.DISPLAY() != null) {
				type = UsageClause.Type.Display;
			} else if (ctx.DISPLAY_1() != null) {
				type = UsageClause.Type.Display1;
			} else if (ctx.DOUBLE() != null) {
				type = UsageClause.Type.Double;
			} else if (ctx.EVENT() != null) {
				type = UsageClause.Type.Event;
			} else if (ctx.FUNCTION_POINTER() != null) {
				type = UsageClause.Type.FunctionPointer;
			} else if (ctx.INDEX() != null) {
				type = UsageClause.Type.Index;
			} else if (ctx.KANJI() != null) {
				type = UsageClause.Type.Kanji;
			} else if (ctx.LOCK() != null) {
				type = UsageClause.Type.Lock;
			} else if (ctx.NATIONAL() != null) {
				type = UsageClause.Type.National;
			} else if (ctx.PACKED_DECIMAL() != null) {
				type = UsageClause.Type.PackedDecimal;
			} else if (ctx.POINTER() != null) {
				type = UsageClause.Type.Pointer;
			} else if (ctx.PROCEDURE_POINTER() != null) {
				type = UsageClause.Type.ProcedurePointer;
			} else if (ctx.REAL() != null) {
				type = UsageClause.Type.Real;
			} else if (ctx.TASK() != null) {
				type = UsageClause.Type.Task;
			} else {
				LOG.warn("unknown usage at {}", ctx);
				type = null;
			}

			result.setType(type);

			usageClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public UsingClause addUsingClause(final DataUsingClauseContext ctx) {
		UsingClause result = (UsingClause) getASGElement(ctx);

		if (result == null) {
			result = new UsingClauseImpl(programUnit, ctx);

			/*
			 * type
			 */
			final UsingClause.Type type;

			if (ctx.CONVENTION() != null) {
				type = UsingClause.Type.Convention;
			} else if (ctx.LANGUAGE() != null) {
				type = UsingClause.Type.Language;
			} else {
				LOG.warn("unknown type at {}", ctx);
				type = null;
			}

			result.setType(type);

			/*
			 * of
			 */
			final ValueStmt ofValueStmt;

			if (ctx.cobolWord() != null) {
				ofValueStmt = createCallValueStmt(ctx.cobolWord());
			} else if (ctx.dataName() != null) {
				ofValueStmt = createCallValueStmt(ctx.dataName());
			} else {
				ofValueStmt = null;
			}

			result.setOfValueStmt(ofValueStmt);

			usingClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ValueClause addValueClause(final DataValueClauseContext ctx) {
		ValueClause result = (ValueClause) getASGElement(ctx);

		if (result == null) {
			result = new ValueClauseImpl(programUnit, ctx);

			for (final DataValueIntervalContext dataValueIntervalContext : ctx.dataValueInterval()) {
				result.addValueInterval(dataValueIntervalContext);
			}

			valueClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public WithLowerBoundsClause addWithLowerBoundClause(final DataWithLowerBoundsClauseContext ctx) {
		WithLowerBoundsClause result = (WithLowerBoundsClause) getASGElement(ctx);

		if (result == null) {
			result = new WithLowerBoundsClauseImpl(programUnit, ctx);

			result.setWithLowerBounds(true);

			withLowerBoundsClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public AlignedClause getAlignedClause() {
		return alignedClause;
	}

	@Override
	public BlankWhenZeroClause getBlankWhenZeroClause() {
		return blankWhenZeroClause;
	}

	@Override
	public CommonOwnLocalClause getCommonOwnLocalClause() {
		return commonOwnLocalClause;
	}

	@Override
	public List<DataDescriptionEntry> getDataDescriptionEntries() {
		return dataDescriptionEntries;
	}

	@Override
	public DataDescriptionEntry getDataDescriptionEntry(final String name) {
		return dataDescriptionEntriesSymbolTable.get(name);
	}

	@Override
	public ExternalClause getExternalClause() {
		return externalClause;
	}

	@Override
	public GlobalClause getGlobalClause() {
		return globalClause;
	}

	@Override
	public IntegerStringClause getIntegerStringClause() {
		return integerStringClause;
	}

	@Override
	public JustifiedClause getJustifiedClause() {
		return justifiedClause;
	}

	@Override
	public List<OccursClause> getOccursClauses() {
		return occursClauses;
	}

	@Override
	public PictureClause getPictureClause() {
		return pictureClause;
	}

	@Override
	public ReceivedByClause getReceivedByClause() {
		return receivedByClause;
	}

	@Override
	public RecordAreaClause getRecordAreaClause() {
		return recordAreaClause;
	}

	@Override
	public RedefinesClause getRedefinesClause() {
		return redefinesClause;
	}

	@Override
	public SignClause getSignClause() {
		return signClause;
	}

	@Override
	public SynchronizedClause getSynchronizedClause() {
		return synchronizedClause;
	}

	@Override
	public ThreadLocalClause getThreadLocalClause() {
		return threadLocalClause;
	}

	@Override
	public Type getType() {
		final Type result;

		if (DataDescriptionEntry.LEVEL_NUMBER_SCALAR == levelNumber) {
			result = Type.Scalar;
		} else {
			result = Type.Group;
		}

		return result;
	}

	@Override
	public TypeClause getTypeClause() {
		return typeClause;
	}

	@Override
	public TypeDefClause getTypeDefClause() {
		return typeDefClause;
	}

	@Override
	public UsageClause getUsageClause() {
		return usageClause;
	}

	@Override
	public UsingClause getUsingClause() {
		return usingClause;
	}

	@Override
	public ValueClause getValueClause() {
		return valueClause;
	}

	@Override
	public WithLowerBoundsClause getWithLowerBoundsClause() {
		return withLowerBoundsClause;
	}

}