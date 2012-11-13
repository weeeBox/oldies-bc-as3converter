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
import bc.help.BcCodeHelper;

public class BcFunctionDeclaration extends BcDeclaration
{
	private List<BcFuncParam> params;
	
	private BcTypeNodeInstance returnTypeInstance;
	private String name;
	private boolean isConstructor;
	private boolean isOverridenConstructor;
	private int defaultParamsCount;

	private BcClassDefinitionNode owner;
	
	private List<BcVariableDeclaration> declaredVars;
	private ListWriteDestination body;

	private static final int TYPE_REGULAR = 0;
	private static final int TYPE_VIRTUAL = 1;
	private static final int TYPE_OVERRIDE = 2;
	private int type = TYPE_REGULAR;
	
	private static final int KIND_REGULAR = 0;
	private static final int KIND_GETTER = 1;
	private static final int KIND_SETTER = 2;
	private static final int KIND_GLOBAL = 3;
	private int kind = KIND_REGULAR;
	
	private StatementListNode statements;

	private boolean isSelector;
	
	public BcFunctionDeclaration(String name)
	{
		this.name = name;
		params = new ArrayList<BcFuncParam>();
		declaredVars = new ArrayList<BcVariableDeclaration>();
	}
	
	public StatementListNode getStatements()
	{
		return statements;
	}
	
	public void setOwner(BcClassDefinitionNode owner)
	{
		this.owner = owner;
	}
	
	public BcClassDefinitionNode getOwner()
	{
		return owner;
	}

	public boolean hasOwner()
	{
		return owner != null;
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

	public BcFuncParam findParam(String name)
	{
		for (BcFuncParam param : params) 
		{
			if (param.getIdentifier().equals(name))
			{
				return param;
			}
		}
		return null;
	}
	
	public void setReturnType(BcTypeNodeInstance returnType)
	{
		this.returnTypeInstance = returnType;
	}
	
	public void setBody(ListWriteDestination body)
	{
		this.body = body;
	}
	
	public BcTypeNodeInstance getReturnTypeInstance()
	{
		return returnTypeInstance;
	}
	
	public BcTypeNode getReturnType()
	{
		return returnTypeInstance != null ? returnTypeInstance.getType() : null;
	}
	
	public boolean hasReturnType()
	{
		return returnTypeInstance != null;
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
	
	public void setSelector()
	{
		isSelector = true;
	}
	
	public boolean isSelector()
	{
		return isSelector;
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
	
	public void setOverride()
	{
		type = TYPE_OVERRIDE;
	}
	
	public void setVirtual()
	{
		type = TYPE_VIRTUAL;
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
	
	public boolean isVirtual()
	{
		return type == TYPE_VIRTUAL;
	}
	
	public boolean isOverride()
	{
		return type == TYPE_OVERRIDE;
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
	
	public boolean hasRestParams()
	{
		int paramsCount = params.size();
		return paramsCount > 0 && params.get(paramsCount - 1).getType() instanceof BcRestTypeNode;
	}
	
	public ListWriteDestination getBody()
	{
		return body;
	}
	
	public BcFunctionDeclaration clone() 
	{
		BcFunctionDeclaration func = new BcFunctionDeclaration(name);
		
		func.params = params;
		func.returnTypeInstance = returnTypeInstance;
		func.name = name;
		func.isConstructor = isConstructor;
		func.isOverridenConstructor = isOverridenConstructor;
		func.defaultParamsCount = defaultParamsCount;
		func.owner = owner;
		func.declaredVars = declaredVars;
		func.body = body;
		func.type = type;
		func.kind = kind;
		func.statements = statements;
		func.isSelector = isSelector;
		
		return func;
	}
	
	public BcFunctionDeclaration createOverridenWithNumDefParams(int numParams)
	{
		BcFunctionDeclaration bcFunc = new BcFunctionDeclaration(name);
		
		bcFunc.isConstructor = isConstructor;
		bcFunc.isOverridenConstructor = isConstructor;
		bcFunc.returnTypeInstance = returnTypeInstance;
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
		IdentifierNode identifier = new IdentifierNode(bcFunc.isConstructor ? BcCodeHelper.thisCallMarker : name, 0);
		ArgumentListNode args = new ArgumentListNode(null, 0);
		args.items.clear(); // this is more like a hack. The first element is added from the constructor and we don't need it
		counter = 0;
		
		for (BcFuncParam param : params)
		{
			if (counter < lastParamIndex)
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
	
	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		if (returnTypeInstance != null)
		{
			result.append(returnTypeInstance.getQualifiedName());
		}
		else
		{
			result.append("void");
		}
		
		result.append(" ");
		
		if (owner != null)
		{
			result.append(owner + "::");
		}
		
		result.append(name);
		result.append("(");
		
		int paramIndex = 0;
		for (BcFuncParam param : params)
		{
			result.append(param.getTypeInstance().getQualifiedName());
			if (++paramIndex < params.size())
			{
				result.append(",");
			}
		}
		
		result.append(")");
		
		return result.toString();
	}
}
