package sg.edu.iss.jam.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class DashboardController  {
	
	//dashboard for artist
		@Autowired
		ArtistInterface aservice;
		@Autowired
		UserInterface uservice;
		
		
		@GetMapping("/dashboard")
		public String showDashboard(Model model) {
			
		//video and music chart	
				Long artistId = (long) 1;
				User artist = aservice.findById(artistId);
				
				List<Media> artistVideos = new ArrayList<Media>();
				List<UserHistory> videoUserHistories=new ArrayList<UserHistory>();
				List<String> videotitle=new ArrayList<String>();
				List<Integer> viewVideoCounts = new ArrayList<Integer>();
				int videosnumber=0;
				int totalVideosCounts=0;
				
				List<Media> artistMusics = new ArrayList<Media>();
				List<UserHistory> musicUserHistories=new ArrayList<UserHistory>();
				List<String> musicTitle=new ArrayList<String>();
				List<Integer> listenMusicCounts = new ArrayList<Integer>();
				int musicsnumber=0;
				int totalMusicsCounts=0;
			
		
				
				List<Channel> artistChannels = (List<Channel>) artist.getChannels();
				
				for(Channel c: artistChannels) {
					if(c.getMediaType() == MediaType.Video) {	
						artistVideos = (List<Media>) c.getChannelMediaList();
						videosnumber=(int)artistVideos.size();
						for(Media v:artistVideos) {
							videotitle.add(v.getTitle());
							videoUserHistories = uservice.findUserHistoryByMediaId(v.getId());
							viewVideoCounts.add(videoUserHistories.size());
						}
					}
				}
				for(int i=0;i<viewVideoCounts.size();i++) {
					totalVideosCounts=totalVideosCounts+viewVideoCounts.get(i);
				}
				
					
					for(Channel c: artistChannels) {
						if(c.getMediaType() == MediaType.Music) {	
							artistMusics = (List<Media>) c.getChannelMediaList();
							musicsnumber=(int)artistMusics.size();
							for(Media v:artistMusics) {
								musicTitle.add(v.getTitle());
								musicUserHistories = uservice.findUserHistoryByMediaId(v.getId());
								listenMusicCounts.add(musicUserHistories.size());
							}
						}
					}
					
					for(int i=0;i<listenMusicCounts.size();i++) {
						totalMusicsCounts=totalMusicsCounts+listenMusicCounts.get(i);
					}
					
	    //products chart
					List<Product> productsInShop = aservice.getProductListByArtistID(artistId);
					int productnumber=productsInShop.size();
					
					model.addAttribute("videos",videotitle);
					model.addAttribute("videocounts",viewVideoCounts);
					model.addAttribute("videosnumber",videosnumber);
					model.addAttribute("totalvideoscount",totalVideosCounts);
					
					model.addAttribute("musics",musicTitle);
					model.addAttribute("musiccounts",listenMusicCounts);
					model.addAttribute("musicsnumber",musicsnumber);
					model.addAttribute("totalmusicscount",totalMusicsCounts);
					model.addAttribute("productnumber",productnumber);
					

		//Line chart with time series:
				
					//Find all userhistory
					List<UserHistory> allUserHistories=uservice.findAllUserHistory();
					int monthNow=LocalDateTime.now().getMonthValue();
					//I: video-find all video userhistory :
					List<Long> artistallVideoId=new ArrayList<Long>();
					Long videoID;
					for(Channel c: artistChannels) {
						if(c.getMediaType() == MediaType.Video) {	
							artistVideos = (List<Media>) c.getChannelMediaList();
							for(int i=0;i<artistVideos.size();i++) {
								videoID=artistVideos.get(i).getId();
								artistallVideoId.add(videoID);
								}
							}
						}
					
					// Time in this year
					List<UserHistory> allVideoUserHistories=new ArrayList<UserHistory>();
					for(UserHistory uh:allUserHistories) {
						if(artistallVideoId.contains(uh.getMediaHistory().getId())&&
								uh.getDatetime().getYear()==LocalDateTime.now().getYear()) {
							allVideoUserHistories.add(uh);
						}
					}
					
					//Count video in a month interval during this year:
					List<Integer> videoCountInMonth=new ArrayList<Integer>();
					for(int i=0;i<monthNow;i++) {
						videoCountInMonth.add(i,0);
					}
					
					for(int i=0;i<monthNow;i++) {
							int count=0;
							for(UserHistory uh:allVideoUserHistories) {
						      if(uh.getDatetime().getMonthValue()==i+1) {
						    	  count++;
						    	  videoCountInMonth.set(i,count);
							    }
						}
					  } 
					
					// Time in last year
					List<UserHistory> allVideoUserHistoriesLastyear=new ArrayList<UserHistory>();
					for(UserHistory uh:allUserHistories) {
						if(artistallVideoId.contains(uh.getMediaHistory().getId())&&
								uh.getDatetime().getYear()==LocalDateTime.now().getYear()-1) {
							allVideoUserHistoriesLastyear.add(uh);
						}
					}
					
					//Count video in a month interval during last year:
					List<Integer> videoCountInMonthLastYear=new ArrayList<Integer>();
					for(int i=0;i<12;i++) {
						videoCountInMonthLastYear.add(i,0);
					}
					
					for(int i=0;i<12;i++) {
							int count=0;
							for(UserHistory uh:allVideoUserHistoriesLastyear) {
						      if(uh.getDatetime().getMonthValue()==i+1) {
						    	  count++;
						    	  videoCountInMonthLastYear.set(i,count);
							    }
						}
					  } 
					
					
					
					
					//II: music-find all music userhistory:
					List<Long> artistallMusicId=new ArrayList<Long>();
					Long musicID;
					for(Channel c: artistChannels) {
						if(c.getMediaType() == MediaType.Music) {	
							artistMusics = (List<Media>) c.getChannelMediaList();
							for(int i=0;i<artistMusics.size();i++) {
								musicID=artistMusics.get(i).getId();
								artistallMusicId.add(musicID);
								}
							}
						}
					// Time in this year
					List<UserHistory> allMusicUserHistories=new ArrayList<UserHistory>();
					for(UserHistory uh:allUserHistories) {
						if(artistallMusicId.contains(uh.getMediaHistory().getId())&&
								uh.getDatetime().getYear()==LocalDateTime.now().getYear()) {
							allMusicUserHistories.add(uh);
						}
					}
					
					//Count music in a month interval during this year:
					List<Integer> musicCountInMonth=new ArrayList<Integer>();
					for(int i=0;i<monthNow;i++) {
						musicCountInMonth.add(i,0);
					}
					
					for(int i=0;i<monthNow;i++) {
							int count=0;
							for(UserHistory uh:allMusicUserHistories) {
						      if(uh.getDatetime().getMonthValue()==i+1) {
						    	  count++;
						    	  musicCountInMonth.set(i,count);
							    }
						}
					  }
					
					// Time in last year
					List<UserHistory> allMusicUserHistoriesLastyear=new ArrayList<UserHistory>();
					for(UserHistory uh:allUserHistories) {
						if(artistallMusicId.contains(uh.getMediaHistory().getId())&&
								uh.getDatetime().getYear()==LocalDateTime.now().getYear()-1) {
							allMusicUserHistoriesLastyear.add(uh);
						}
					}
					
					//Count music in a month interval during last year:
					List<Integer> musicCountInMonthLastyear=new ArrayList<Integer>();
					for(int i=0;i<12;i++) {
						musicCountInMonthLastyear.add(i,0);
					}
					
					for(int i=0;i<12;i++) {
							int count=0;
							for(UserHistory uh:allMusicUserHistoriesLastyear) {
						      if(uh.getDatetime().getMonthValue()==i+1) {
						    	  count++;
						    	  musicCountInMonthLastyear.set(i,count);
							    }
						}
					  }
					
					
					model.addAttribute("videodata",videoCountInMonth);
					model.addAttribute("videodatalastyear",videoCountInMonthLastYear);
					model.addAttribute("musicdata",musicCountInMonth);
					model.addAttribute("musicdatalastyear",musicCountInMonthLastyear);
//					model.addAttribute("date",stringTimePoint);
					

					
	//Line chart with Subscribe
					
					
					
					
					
					//Search video counts in month interval during this year:
//					List<UserHistory> monthNowUserHistories=new ArrayList<UserHistory>();
//					List<Integer> allVideoCountInOneMonth= new ArrayList<Integer>();
	//
//					int monthNow=LocalDateTime.now().getMonthValue();
//					List<Integer> videoCountInMonth=new ArrayList<Integer>();
//					for(int i=0;i<monthNow;i++) {
//						videoCountInMonth.add(i,0);
//					}
	//	
//					for(UserHistory uh:allVideoUserHistories) {
//						if(uh.getDatetime().getMonthValue()==monthNow) {
//							    monthNowUserHistories.add(uh); 
//								allVideoCountInOneMonth.add(monthNowUserHistories.size());
//								videoCountInMonth.set(monthNow-1,allVideoCountInOneMonth.size());
//						}
//					}
					
					//time interval with month in this year:
//					List<LocalDateTime> timePoints = new ArrayList<LocalDateTime>();
//					timePoints.add(LocalDateTime.now());
//					for(int i=1;i<monthNow;i++) {
//						timePoints.add(LocalDateTime.now().minusMonths(i));
//					}
//					
//					List<String> stringTimePoint=new ArrayList<String>();
//					for(int i=monthNow-1;i>=0;i--) {
//						stringTimePoint.add(timePoints.get(i).toString().substring(0,7));
//					}
					
					return "dashboard";	
		}
}

