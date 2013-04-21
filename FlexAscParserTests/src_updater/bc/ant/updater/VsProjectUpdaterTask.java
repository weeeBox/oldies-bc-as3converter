package bc.ant.updater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import bc.utils.filesystem.FileUtils;

public class VsProjectUpdaterTask extends Task
{
	private static final String COMMENT_MARKER = "Target group";
	private static final String ELEMENT_ITEM_GROUP = "ItemGroup";
	private static final String ELEMENT_COMPILE = "Compile";
	private static final String ATTRIBUTE_INCLUDE = "Include";
	
	private File projectFile;
	private File projectDir;
	
	private List<FileSet> sets;
	
	public VsProjectUpdaterTask() 
	{
		sets = new ArrayList<FileSet>();
	}
	
	@Override
	public void execute() throws BuildException 
	{
		checkProjectFile();
		
		projectDir = projectFile.getParentFile();
		
		Document doc = readProjectXMLDoc();
		updateProjectFile(doc);
	}
	
	private void updateProjectFile(Document doc)
	{	
		update(doc);
		writeProjectXMLDoc(doc);
	}

	/* Clear */

	private void removeElements(List<Element> elements) 
	{
		for (Element e : elements) 
		{
			removeElement(e);
		}
	}
	
	private void removeElement(Element element) 
	{
		element.getParent().remove(element);
	}
	
	/* Update */
	
	private void update(Document doc)
	{
		Element target = findTargetItemGroupElement(doc);
		if (target == null)
		{
			target = doc.getRootElement().addElement(ELEMENT_ITEM_GROUP);
			target.addComment(COMMENT_MARKER);
		}
		else
		{
			removeElements(target.elements(ELEMENT_COMPILE));
		}
		
		List<File> sourceFiles = collectSourceFiles(sets);
		addSourceFiles(target, sourceFiles);
	}

	private Element findTargetItemGroupElement(Document doc) 
	{
		List<Element> itemGroups = collectItemGroups(doc);
		for (Element itemGroup : itemGroups) 
		{
			if (containsTargetElement(itemGroup))
			{
				return itemGroup;
			}
		}
		
		return null;
	}
	
	private boolean containsTargetElement(Element itemGroup) 
	{
		List<Node> nodes = itemGroup.content();
		for (Node node : nodes) 
		{
			if (node instanceof Comment)
			{
				String text = ((Comment) node).getText().trim();
				if (COMMENT_MARKER.equals(text))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void addSourceFiles(Element element, List<File> list) 
	{
		for (File file : list) 
		{
			String path = FileUtils.makeRelativePath(projectDir, file);
			element.addElement(ELEMENT_COMPILE).addAttribute(ATTRIBUTE_INCLUDE, path);
			
			System.out.println("Add source path: " + path);
		}
	}
	
	/* Source files */
	
	private List<File> collectSourceFiles(List<FileSet> sets) 
	{
		List<File> files = new ArrayList<File>();
		collectSourceFiles(sets, files);
		return files;
	}
	
	private void collectSourceFiles(List<FileSet> sets, List<File> list) 
	{
		for (FileSet fileSet : sets)
		{
			collectSourceFiles(fileSet, list);
		}
	}
	
	private void collectSourceFiles(FileSet set, List<File> list)
	{
		DirectoryScanner scanner = set.getDirectoryScanner();
		File basedir = scanner.getBasedir();
		String[] filenames = scanner.getIncludedFiles();
		for (String filename : filenames)
		{
			list.add(new File(basedir, filename));
		}
	}

	/* Setters */

	public void setProjectFile(File projectFile) 
	{
		this.projectFile = projectFile;
	}
	
	public void addFileset(FileSet set)
	{
		sets.add(set);
	}
	
	/* Helpers */
	
	private void checkProjectFile() 
	{
		checkArgument(projectFile, "projectFile");
		
		if (!projectFile.exists())
		{
			throw new BuildException("C# project file doesn't exist: " + projectFile.getAbsolutePath());
		}
		
		if (projectFile.isDirectory())
		{
			throw new BuildException("C# project file is a directory: " + projectFile.getAbsolutePath());
		}
	}
	
	private void checkArgument(File file, String paramName) 
	{
		if (file == null)
		{
			throw new BuildException(String.format("Missing '%s' argument", paramName));
		}
	}
	
	private Document readProjectXMLDoc() 
	{
		try 
		{
			return new SAXReader().read(projectFile);
		} 
		catch (DocumentException e) 
		{
			throw new BuildException(e);
		}
	}
	
	private void writeProjectXMLDoc(Document doc)
	{
		try 
		{
			FileOutputStream stream = new FileOutputStream(projectFile);
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(stream, format);
			writer.write(doc);
			writer.flush();
			
			stream.close();
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private List<Element> collectItemGroups(Document doc) 
	{
		return doc.getRootElement().elements("ItemGroup");
	}
}
