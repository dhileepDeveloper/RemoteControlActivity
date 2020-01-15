package com.scopeInternational.rca.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scopeInternational.rca.model.Commander;

@Controller
public class HomeController {

	private Commander globalCommander = null;
	private boolean isCmdExecuted = false;
	private Commander outputCommander = null;
	private static final int CMD_TIMEOUT = 15000;
	
	@Value("#{commandList}")
	private HashMap<String,String> commandMap;
	
	@RequestMapping(value="home", method = RequestMethod.GET)
	public String home()
	{
		return "commandPage";
	}
	
	@ModelAttribute("commander")
	public Commander prefillCommander(Model model)
	{	
		model.addAttribute("commandMap", commandMap);
		if(!isCmdExecuted)
		{
			return new Commander();
		}
		else
		{   System.out.println("prefillCommander outputCommander " + outputCommander);
			return outputCommander;
		}
			
	}
		
	@RequestMapping(value="executeCommand", method = RequestMethod.POST)
	public String executeCommand(@ModelAttribute("commander") Commander commander,Model model)
	{
		System.out.println("Commander is " + commander);
		int waitedTime =0;
		globalCommander = commander;
		while(!isCmdExecuted)
		{
			try {
				if(waitedTime >= CMD_TIMEOUT)
				{
					outputCommander.setTextArea("TIMEOUT HAPPENS...... SORRY");
					break;
				}
				else
				{
					Thread.sleep(1000);
					waitedTime += 1000;
				}
				System.out.println("Thread is sleeping ... ");
				System.out.println("waitedTime is ... " + waitedTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Loop broken");
		isCmdExecuted = false;
		model.addAttribute("commander",outputCommander);
		return "commandPage";
	} 
	
	@RequestMapping(value="getCommand", method=RequestMethod.GET)
	public @ResponseBody Commander showCommands()
	{
		Commander commander = globalCommander;
		System.out.println("globalCommander is " + globalCommander);
		return commander;
	}
	
	@RequestMapping(value="processOutput",method=RequestMethod.POST)
	public @ResponseBody Commander processOutput(@RequestBody Commander commander)
	{
		System.out.println("Transmit commander is called " + commander);
		globalCommander = null;
		outputCommander = commander;
		isCmdExecuted =true;
		System.out.println("outputCommander is " + outputCommander);
		return  outputCommander;
		
	}
	
}
