package bc.lang;

import macromedia.asc.parser.DefinitionNode;

public class BcModuleDeclarationEntry
{
	private BcDeclaration declaration;
	private DefinitionNode definition;
	
	public BcModuleDeclarationEntry(BcDeclaration declaration, DefinitionNode definition)
	{
		this.declaration = declaration;
		this.definition = definition;
	}

	public BcDeclaration getDeclaration()
	{
		return declaration;
	}

	public DefinitionNode getDefinition()
	{
		return definition;
	}
}
