package bc.lang;

import java.util.ArrayList;
import java.util.List;

import macromedia.asc.parser.ArgumentListNode;
import macromedia.asc.parser.CallExpressionNode;
import macromedia.asc.parser.ExpressionStatementNode;
import macromedia.asc.parser.GetExpressionNode;
import macromedia.asc.parser.IdentifierNode;
import macromedia.asc.parser.ListNode;
import macromedia.asc.parser.MemberExpressionNode;
import macromedia.asc.parser.ReturnStatementNode;
import macromedia.asc.parser.StatementListNode;
import bc.code.ListWriteDestination;
import bc.help.BcCodeCpp;

public class BcFunctionDeclaration extends BcDeclaration
{
	private List<BcFuncParam> params;
	
	public static String thisCallMarker;
	public static String superCallMarker;
	
	private BcTypeNode returnType;
	private String name;
	private boolean isConstructor;
	private boolean isOverridenConstructor;
	private int defaultParamsCount;

	private List<BcVariableDeclaration> declaredVars;
	private ListWriteDestination body;
	
	private static final int KIND_REGULAR = 0;
	private static final int KIND_GETTER = 1;
	private static final int KIND_SETTER = 2;
	private static final int KIND_GLOBAL = 3;
	private int kind = KIND_REGULAR;
	
	private StatementListNode statements;
	
	public BcFunctionDeclaration(String name)
	{
		this.name = name;
		params = new ArrayList<BcFuncParam>();
	}
	
	public StatementListNode getStatements()
	{
		return statements;
	}
	
	public void setStatements(StatementListNode statements)
	{
		this.statements = statements;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addParam(BcFuncParam param)
	{
		params.add(param);
		if (param.hasDefaultInitializer())
		{
			defaultParamsCount++;
		}
	}

	public void setReturnType(BcTypeNode returnType)
	{
		this.returnType = returnType;
	}
	
	public void setBody(ListWriteDestination body)
	{
		this.body = body;
	}
	
	public BcTypeNode getReturnType()
	{
		return returnType;
	}
	
	public boolean hasReturnType()
	{
		return returnType != null;
	}
	
	public void setConstructorFlag(boolean flag)
	{
		isConstructor = flag;
	}
	
	public void setDeclaredVars(List<BcVariableDeclaration> declaredVars)
	{
		this.declaredVars = declaredVars;
	}
	
	public List<BcVariableDeclaration> getDeclaredVars()
	{
		return declaredVars;
	}
	
	public BcVariableDeclaration findVariable(String name)
	{
		for (BcVariableDeclaration bcVar : declaredVars)
		{
			if (bcVar.getIdentifier().equals(name))
			{
				return bcVar;
			}
		}
		
		return null;
	}
	
	public boolean isConstructor()
	{
		return isConstructor;
	}
	
	public boolean isOverridenConstructor()
	{
		return isOverridenConstructor;
	}
	
	public void setGetter()
	{
		kind = KIND_GETTER;
	}
	
	public void setSetter()
	{
		kind = KIND_SETTER;
	}
	
	public void setGlobal()
	{
		kind = KIND_GLOBAL;
	}
	
	public boolean isSetter()
	{
		return kind == KIND_SETTER;
	}

	public boolean isGetter()
	{
		return kind == KIND_GETTER;
	}
	
	public boolean isGlobal()
	{
		return kind == KIND_GLOBAL;
	}
	
	public boolean isProperty()
	{
		return kind == KIND_GETTER || kind == KIND_SETTER;
	}

	public int getDefaultParamsCount()
	{
		return defaultParamsCount;
	}
	
	public boolean hasDefaultParams()
	{
		return defaultParamsCount > 0;
	}
	
	public List<BcFuncParam> getParams()
	{
		return params;
	}
	
	public ListWriteDestination getBody()
	{
		return body;
	}
	
	public BcFunctionDeclaration createOverridenWithNumDefParams(int numParams)
	{
		BcFunctionDeclaration bcFunc = new BcFunctionDeclaration(name);
		
		bcFunc.isConstructor = isConstructor;
		bcFunc.isOverridenConstructor = isConstructor;
		bcFunc.returnType = returnType;
		bcFunc.kind = kind;
		bcFunc.declaredVars = new ArrayList<BcVariableDeclaration>(declaredVars);
		bcFunc.modifiers = modifiers;
		
		ArrayList<BcFuncParam> newParams = new ArrayList<BcFuncParam>();
		int lastParamIndex = params.size() - (defaultParamsCount - numParams);
		int counter = 0;
		for (BcFuncParam param : params)
		{
			if (counter == lastParamIndex)
			{
				break;
			}
			newParams.add(param);
			counter++;			
		}
		bcFunc.params = newParams;
		
		// this is a big mess. Just take it as is
		IdentifierNode identifier = new IdentifierNode(bcFunc.isConstructor ? thisCallMarker : name, 0);
		ArgumentListNode args = new ArgumentListNode(null, 0);
		args.items.clear(); // this is more like a hack. The first element is added from the constructor and we don't need it
		counter = 0;
		int defaultParamsStartIndex = params.size() - defaultParamsCount;
		
		for (BcFuncParam param : params)
		{
			if (counter == lastParamIndex + 1)
			{
				break;
			}
			if (counter < defaultParamsStartIndex)
			{
				args.items.add(new MemberExpressionNode(null, new GetExpressionNode(new IdentifierNode(param.getIdentifier(), 0)), 0));
			}
			else
			{
				assert param.hasDefaultInitializer();
				args.items.add(param.getDefaultInitializer());
			}
			counter++;			
		}		
		
		CallExpressionNode selector = new CallExpressionNode(identifier, args);
		MemberExpressionNode member = new MemberExpressionNode(null, selector, 0);
		StatementListNode newStatements;
		if (hasReturnType())
		{
			newStatements = new StatementListNode(new ReturnStatementNode(member));
		}
		else
		{
			ListNode list = new ListNode(null, member, 0);
			ExpressionStatementNode exprNode = new ExpressionStatementNode(list);
			newStatements = new StatementListNode(exprNode);
		}
		
		bcFunc.statements = newStatements;
		
		return bcFunc;
	}
}
