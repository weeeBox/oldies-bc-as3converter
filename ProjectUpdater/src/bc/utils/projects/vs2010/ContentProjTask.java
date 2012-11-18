package bc.utils.projects.vs2010;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import bc.utils.filesystem.FileUtils;

public class ContentProjTask extends Task
{
	private static final String ELEMENT_COMPILE = "Compile";
	private static final String ATTRIBUTE_INCLUDE = "Include";
	
	private static final String SOURCE_FILE_EXT = ".cs";
	
	private File projectFile;
	private File sourceDir;
	
	public ContentProjTask() 
	{
	}
	
	public static void main(String[] args) 
	{
		ContentProjTask task = new ContentProjTask();
		task.projectFile = new File(args[0]);
		task.sourceDir = new File(args[1]);
		task.execute();
	}
	
	@Override
	public void execute() throws BuildException 
	{
		checkProjectFile();
		checkSourceDir();
		
		Document doc = readProjectXMLDoc();
		updateProjectFile(doc);
	}
	
	private void updateProjectFile(Document doc)
	{	
		clear(doc);
		update(doc);
		writeProjectXMLDoc(doc);
	}

	/* Clear */

	private void clear(Document doc) 
	{
		String sourceDirName = sourceDir.getName();
		
		List<Element> compileElements = new ArrayList<>();
		
		List<Element> itemGroups = collectItemGroups(doc);
		for (Element itemGroup : itemGroups) 
		{
			collectCompileElements(itemGroup, compileElements, sourceDirName);
		}
		removeElements(compileElements);
	}

	private void removeElements(List<Element> compileElements) 
	{
		for (Element e : compileElements) 
		{
			Element parent = e.getParent();
			
			removeElement(e);
			if (parent.nodeCount() == 0)
			{
				removeElement(parent);
			}
		}
	}

	private void removeElement(Element element) 
	{
		element.getParent().remove(element);
	}
	
	private void collectCompileElements(Element itemGroup, List<Element> list, String sourceDirName) 
	{
		List<Element> children = itemGroup.elements(ELEMENT_COMPILE);
		for (Element child : children) 
		{
			String include = child.attributeValue(ATTRIBUTE_INCLUDE);
			if (include != null && include.startsWith(sourceDirName))
			{
				list.add(child);
			}
		}
	}
	
	/* Update */
	
	private void update(Document doc)
	{
		Element target = findTargetItemGroupElement(doc);
		if (target == null)
		{
			throw new BuildException("Unable to find target 'ItemGroup' element");
		}
		
		List<File> sourceFiles = collectSourceFiles(sourceDir);
		addSourceFiles(target, sourceFiles);
	}

	private Element findTargetItemGroupElement(Document doc) 
	{
		List<Element> itemGroups = collectItemGroups(doc);
		for (Element itemGroup : itemGroups) 
		{
			if (containsCompileIncludeElement(itemGroup))
			{
				return itemGroup;
			}
		}
		
		return null;
	}
	
	private boolean containsCompileIncludeElement(Element itemGroup) 
	{
		List<Element> children = itemGroup.elements(ELEMENT_COMPILE);
		for (Element child : children) 
		{
			if (child.attributeValue(ATTRIBUTE_INCLUDE) != null)
			{
				return true;
			}
		}
		return false;
	}
	
	private void addSourceFiles(Element element, List<File> list) 
	{
		for (File file : list) 
		{
			String path = FileUtils.makeRelativePath(sourceDir.getParentFile(), file);
			element.addElement(ELEMENT_COMPILE).addAttribute(ATTRIBUTE_INCLUDE, path);
		}
	}
	
	/* Source files */
	
	private List<File> collectSourceFiles(File dir) 
	{
		List<File> files = new ArrayList<File>();
		collectSourceFiles(dir, files);
		return files;
	}
	
	private void collectSourceFiles(File file, List<File> list) 
	{
		if (file.isDirectory())
		{
			File[] files = FileUtils.listFilesAndDirectories(file, SOURCE_FILE_EXT);
			for (File childFile : files) 
			{
				collectSourceFiles(childFile, list);
			}
		}
		else
		{
			list.add(file);
		}
	}
	
	/* Setters */

	public void setProjectFile(File projectFile) 
	{
		this.projectFile = projectFile;
	}
	
	public void setSourceDir(File sourceDir) 
	{
		this.sourceDir = sourceDir;
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
	
	private void checkSourceDir() 
	{
		checkArgument(sourceDir, "sourceDir");
		
		if (!sourceDir.exists())
		{
			throw new BuildException("Source directory doesn't exist: " + sourceDir.getAbsolutePath());
		}
		
		if (!sourceDir.isDirectory())
		{
			throw new BuildException("Source directory is not a directory: " + sourceDir.getAbsolutePath());
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
