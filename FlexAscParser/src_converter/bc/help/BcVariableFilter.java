package bc.help;

import bc.lang.BcVariableDeclaration;

public interface BcVariableFilter
{
	boolean accept(BcVariableDeclaration var);
}
