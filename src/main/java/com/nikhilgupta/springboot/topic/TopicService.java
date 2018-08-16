package com.nikhilgupta.springboot.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service // marks as spring buisness service
public class TopicService {

	private List<Topic> topics = new ArrayList<>(Arrays.asList(
			new Topic("spring","spring Framework","Spring Framework description"),
			new Topic("java","core java","core java description"),
			new Topic("javascript","javascript Framework","javascript description")
			));
	
	public List<Topic> getAllTopics(){
		return topics;
	}
	
	public Topic getTopic(String id) {
		return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}
	
	public void addTopic(Topic topic) {
		topics.add(topic);
	}
	
	public void updateTopic(String id,Topic topic) {
		for(int i=0;i<topics.size();i++) {
			Topic topic2 = topics.get(i);
			if(topic2.getId().equals(id)) {
				topics.set(i, topic);
				return;
			}
		}
	}
	
	public void deleteTopic(String id) {
		topics.removeIf(t -> t.getId().equals(id));
	}
}
