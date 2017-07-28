package com.example.testdecryption;

import java.io.File;
import java.util.ArrayList;

public class Story
{

	private File mRealFile;
	private String title;
	private ArrayList<Paragraph> paragraphs;

	public Story(){
		
	}
	
	public Story(File file)
	{
		mRealFile = file;

		parse();
	}

	private void parse()
	{
		if (mRealFile == null)
		{
			return;
		}

	}

	public File getmRealFile()
	{
		return mRealFile;
	}

	public void setmRealFile(File mRealFile)
	{
		this.mRealFile = mRealFile;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public ArrayList<Paragraph> getParagraphs()
	{
		return paragraphs;
	}

	public void setParagraphs(ArrayList<Paragraph> paragraphs)
	{
		this.paragraphs = paragraphs;
	}

}
