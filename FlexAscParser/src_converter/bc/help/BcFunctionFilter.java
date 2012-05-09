package bc.help;

import bc.lang.BcFunctionDeclaration;

public interface BcFunctionFilter
{
	boolean accept(BcFunctionDeclaration func);
}
