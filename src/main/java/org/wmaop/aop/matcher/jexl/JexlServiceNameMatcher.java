package org.wmaop.aop.matcher.jexl;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;
import org.wmaop.aop.matcher.MatchResult;
import org.wmaop.aop.matcher.Matcher;
import org.wmaop.aop.pipeline.FlowPosition;
import org.wmaop.util.jexl.JexlExpressionFactory;

public class JexlServiceNameMatcher implements Matcher<FlowPosition> {

	private final Expression expression;
	private final String sid;
	public JexlServiceNameMatcher(String sid, String expr) {
		this.sid = sid;
		expression = createExpression(sid, expr);
	}

	public MatchResult match(FlowPosition flowPosition) {
		final JexlContext ctx = new MapContext();
		ctx.set("serviceName", flowPosition.toString());
		Object result = expression.evaluate(ctx);
		verifyExpressionResult(sid, result);
		if ((Boolean) result) {
			return new MatchResult(true, sid);
		}
		return MatchResult.FALSE;
	}

	private Expression createExpression(String name, String exprText) {
		Expression compiledExpr = JexlExpressionFactory.getEngine().createExpression(exprText);
		Object result = compiledExpr.evaluate(new MapContext());
		verifyExpressionResult(name, result);
		return compiledExpr;
	}
	
	private void verifyExpressionResult(String name, Object result) {
		if (!(result instanceof Boolean)) {
			throw new RuntimeException("Cannot parse expression named '" + name
					+ "' to get boolean, instead got " + result.getClass().getSimpleName() + ": " + result);
		}
	}
}
