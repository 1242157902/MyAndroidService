package cn.ncut.algorim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.ncut.utils.DateUtils;
import cn.ncut.wxdao.FrequentItemDao;
import cn.ncut.wxdao.LocationClassifyDao;
import cn.ncut.wxdomain.LoctionClassify;
import cn.ncut.wxdomain.Prefer;



/**
 * 
 * <p>Title：        myApriori<p>
 * <p>Description:  Aprior算法，获得关联推荐的项<p>
 * @date:           2016年3月9日下午6:34:32
 * @author:         ysl
 * @version         1.0
 */
public class myApriori {

        static private boolean endTag = false ;
        static private List<List<String>> cItemset ;
        static private List<List<String>> ckItemset ;
        static private Map<List<String>, Integer> lItemset ;
        static private Map<List<String>,Integer>  lkItemset ;
        
        static List<List<String>> record = new ArrayList<List<String>> () ;
        
        //最小支持度域
        static int  MIN_SUPPORT =7;
        static double min_support = 0.0;
        static Map<List<String>, Double > confItemset = new HashMap<List<String>,Double >() ;
        
        
        public static  List<List<String>> getDataSet (String timeString)
        {
                return FileReader.getData(timeString );
        }
        
        /**
         * 
         * @return:       List<List<String>> 
         * @return
         * <p>Description: 得到第一个候选集<p>
         * @date:          2016年3月9日下午6:33:41
         * @author         ysl
         */
        public static List<List<String>> getFirstCandidate ()
        {
               
                List<List<String>> cItemset = new ArrayList<List<String>> () ;
                List<String> tempLine = new ArrayList<String>() ;
               
                for( int i = 0 ; i < record.size() ; i++ )
                {
                        
                        for (int j = 0 ; j < record.get(i).size(); j++)
                        {
                                if(tempLine.contains(record.get(i).get(j))) ;
                                else
                                {
                                        tempLine.add(record.get(i).get(j)) ;
                                        //System.out.println(record.get(i).get(j));
                                }
                        }
                        
                }
               
                for ( int i = 0 ; i < tempLine.size() ;i++)
                {
                        List<String> str = new ArrayList<String>() ;
                        str.add(tempLine.get(i));
                        cItemset.add(str) ;
                        
                }
               
                return cItemset ;
        }
        
        static Map<List<String>,Integer> getSupportedItemset( List<List<String>> cItemset )
        {
                Map<List<String>,Integer> supportedItemset = new HashMap<List<String>,Integer> () ;
               
                boolean end = true ;
               
                for( int i = 0 ; i < cItemset.size(); i++ )
                {
                        int count = countFrequent ( cItemset.get(i)) ;
                        
                        if( count >= MIN_SUPPORT )
                        {
                        supportedItemset.put(cItemset.get(i), count) ;
                        end = false ;
                        }
                }
                endTag = end ;
                //System.out.println("value of the endTag here !!!"+endTag);
                return supportedItemset  ;
        }
        
        /**
         * 
         * @return:       int 
         * @param list
         * @return
         * <p>Description: 计算出现的频率<p>
         * @date:          2016年3月9日下午6:33:09
         * @author         ysl
         */
        static int countFrequent ( List<String> list)
        {
               
                int count = 0 ;
               
                for ( int i = 0 ; i < record.size() ; i++ )
                {
                        boolean curRecordLineNotHave = false ;
                        
                        for ( int k = 0 ; k < list.size(); k++)
                        {
                                if(!record.get(i).contains(list.get(k)))
                                {
                                        curRecordLineNotHave = true ;
                                        break ;
                                }
                        }
                        
                        if(curRecordLineNotHave == false )
                        {
                                count++ ;
                        }
                }
               
                return count ;
        }
        
        

		/**
		 * 
		 * @return:       List<List<String>> 
		 * @param lItemset
		 * @return
		 * <p>Description: 获得下一个候选集<p>
		 * @date:          2016年3月9日下午6:32:41
		 * @author         ysl
		 */
        private static List<List<String>> getNextCandidate ( Map<List<String>,Integer> lItemset )
        {
                List<List<String>> nextItemset = new ArrayList<List<String>>() ;
               
                List<List<String>> preItemset = getLItemset(lItemset ) ;
               
                int count = 0 ;
               
                for ( int i = 0 ; i < preItemset.size() ; i++ )
                {
                        List<String> tempLine = new ArrayList<String> () ;
                        tempLine.addAll(preItemset.get(i)) ;
                  
                        for( int j = i+1 ; j < preItemset.size(); j++)
                        {
                                 if( preItemset.get(i).size() == preItemset.get(j).size())
                                 {
                                                              
                                         if( 1 == differElemNum(preItemset.get(i),preItemset.get(j)))
                                         {
                                                 int index = getDifferIndex ( preItemset.get(i), preItemset.get(j)) ;
                                                
                       
                                                 tempLine.add(preItemset.get(j).get(index)) ;
                                                
                     
                                                 if( isSubSets ( tempLine, preItemset))
                                                 {
                                                                                       
                                                         List<String> aLine = new ArrayList() ;
                                                         
                                                         for(int m = 0 ; m < tempLine.size() ;m++)
                                                         {
                                                                 aLine.add(tempLine.get(m));
                                                         }
                                                         
                                                         if( nextItemSetNotHave( aLine, nextItemset ))
                                                                 nextItemset.add(aLine) ;
                                                         
                                                 }
                                         }
                                 }//outer if
                                 
                                  
                                 if(tempLine.size()>0)
                                 {
                                	 tempLine.remove(tempLine.size()-1 ) ;
                                 }
                        }//for j
                }
                                
                 return nextItemset ;
                                                
        }
        
        
        private static boolean nextItemSetNotHave( List<String> aLine , List<List<String>> nextItemset )
        {
                boolean notHave = true ;
               
                for( int i = 0 ; i < nextItemset.size(); i++ )
                {
                        if(aLine.equals(nextItemset.get(i)))
                        {
                                notHave = false ;
                        }
                }
               
                return notHave ;
        }
        
        
        private static int getDifferIndex ( List<String> list1 , List<String> list2)
        {
                int index = -1 ;
               
                for ( int i = 0 ; i < list1.size() ; i++ )
                {
                        for( int j = 0 ; j < list1.size(); j++ )
                        {
                                if ( !list1.get(i).equals(list2.get(j)))
                                {
                                        index = j ;
                                }
                        }
                        
                        if ( index != -1 )
                                break ;
                }
               
                return index ;
        }
        /**
         * 
         * @return:       int 
         * @param list1
         * @param list2
         * @return
         * <p>Description: 判断每行中是否包含该项<p>
         * @date:          2016年3月9日下午6:31:43
         * @author         ysl
         */
        private static int differElemNum ( List<String> list1, List<String>list2 )
        {
                int count = 0 ;
               
                boolean flag ;
               
                for( int i = 0 ; i < list1.size() ; i++ )
                {
                        flag = true ;
                        
                        for(int j = 0 ; j < list1.size(); j++ )
                        {
                                if(list1.get(i).equals(list2.get(j)))
                                {
                                         flag = false ;
                                        break;
                                }
                        }
                        
                        if( flag == true )
                        {
                                count++ ;
                        }
                }
               
                return count ;
        }
         
        
        
      
        /**
         * 
         * @return:       boolean 
         * @param tempList
         * @param lItemset
         * @return
         * <p>Description: 计算子集的数目<p>
         * @date:          2016年3月9日下午6:30:30
         * @author         ysl
         */
        private static boolean isSubSets ( List<String> tempList , List<List<String>> lItemset)
        {
               
                boolean flag = false ;
               
                for ( int i = 0 ; i < tempList.size() ; i++ )
                {
                        List<String> testLine = new ArrayList() ;
                        
                        for (int j = 0 ; j < tempList.size(); j++ )
                        {
                                if (i!= j )
                                {
                                        testLine.add(tempList.get(j)) ;
                                }
                        }
                        
                         for ( int k = 0 ; k < lItemset.size() ; k++ )
                        {
                                if ( testLine.equals(lItemset.get(k)))
                                {
                                        flag = true ;
                                        break ;
                                }
                        }
                        
                        
                        if ( flag == false )
                        {
                              
                                return false ;
                        }
                }
               
               
                return flag ; //return true ;
               
        }
              
        /**
         * 
         * @return:       List<List<String>> 
         * @param lItemset
         * @return
         * <p>Description:得到Itemset集合 <p>
         * @date:          2016年3月9日下午6:29:55
         * @author         ysl
         */
        private static List<List<String>> getLItemset ( Map<List<String>, Integer> lItemset )
        {
                List<List<String>> itemset = new ArrayList<List<String>> () ;
               
                Iterator<Map.Entry<List<String>, Integer>> iterator = lItemset.entrySet().iterator();
                Entry<List<String>, Integer> entry ;
               
               
                while ( iterator.hasNext() )
                {
                        entry = iterator.next();
                        List<String> key = entry.getKey() ;
                        
                        itemset.add(key) ;
                        
                        
                }
                return itemset ;
        }
        
        public static void main ( String [] args ) throws Exception
        {
        	String timeString ="2015-08-01";
                record =getDataSet(timeString) ;
                MIN_SUPPORT = (int) (record.size()*0.6);
                System.out.println("最小支持度域值为："+MIN_SUPPORT);
                System.out.println("开始运行时间为：----------------"+ System.currentTimeMillis());
                 cItemset = getFirstCandidate() ;
                lItemset = getSupportedItemset( cItemset ) ;
                //打印第一次的候选集
                 printfLKitemset ( lItemset) ;
               
               
               
                  while ( endTag != true )
                 {
                         ckItemset = getNextCandidate(lItemset ) ;
                         lkItemset = getSupportedItemset ( ckItemset ) ;
                        
                         if(lkItemset.size() != 0 )
                        	 // printfLKitemset ( lkItemset) ;
                         insertLKitemset ( lkItemset,timeString) ;
                        
                         cItemset = ckItemset ;
                         lItemset = lkItemset ;
                 }  
                 System.out.println("运行后的时间：---------------"+System.currentTimeMillis());
                  System.out.println("finish ") ;
                 
                 
        }
        /**
         * 
         * @return:       void 
         * @param currentDate
         * @param minSupport
         * @throws Exception
         * <p>Description: 接受传进来的参数<p>
         * @date:          2016年4月18日下午9:55:12
         * @author         ysl
         */
        public  void   referenceDataMining ( String currentDate,String minSupport) throws Exception
        {
        	String timeString =null;
        	
        	if(currentDate==null)
        	{
        		timeString = DateUtils.getCurrentDate().substring(0,10);
        	}else
        	{
        		timeString = currentDate;
        	}
        	record =getDataSet(timeString) ;
        	if(minSupport!=null)
        	{
        		min_support = Double.parseDouble(minSupport);
        		if(min_support<=1&&min_support>0)
        		{
        			MIN_SUPPORT = (int) (record.size()*min_support);
        		}
        		else
        		{
        			MIN_SUPPORT = (int) (record.size()*0.6);
        		}
        	}
        	
        	
        	System.out.println("最小支持度域值为："+MIN_SUPPORT);
        	System.out.println("开始运行时间为：----------------"+ System.currentTimeMillis());
        	cItemset = getFirstCandidate() ;
        	lItemset = getSupportedItemset( cItemset ) ;
        	//打印第一次的候选集
        	printfLKitemset ( lItemset) ;
        	
        	
        	
        	while ( endTag != true )
        	{
        		ckItemset = getNextCandidate(lItemset ) ;
        		lkItemset = getSupportedItemset ( ckItemset ) ;
        		
        		if(lkItemset.size() != 0 )
        			// printfLKitemset ( lkItemset) ;
        		insertLKitemset ( lkItemset,timeString) ;
        		
        		cItemset = ckItemset ;
        		lItemset = lkItemset ;
        	}  
        	System.out.println("运行后的时间：---------------"+System.currentTimeMillis());
        	System.out.println("finish ") ;
        }
   
        
        /**
         * 
         * @return:       void 
         * @param lkItemset
         * <p>Description: 打印数据<p>
         * @date:          2016年3月9日下午6:29:37
         * @author         ysl
         */
         private static void printfLKitemset ( Map<List<String> , Integer> lkItemset )
         {
                 Iterator<Entry<List<String>,Integer>> iterator = lkItemset.entrySet().iterator();
                 
                 Entry<List<String>,Integer> entry ;
                 
                 while ( iterator.hasNext() )
                 {
                         entry = iterator.next() ;
                        
                         List<String> key = entry.getKey() ;
                         Integer value = entry.getValue() ;
                        
                         System.out.println("the key : ");
                        
                         for ( int i = 0 ; i < key.size() ; i++ )
                         {
                                 System.out.print(key.get(i));
                                 System.out.print(",");
                         }
                        
                         System.out.println("the value : "+ value.intValue());
                        
                 }
         }
        
         /**
          * 
          * @return:       void 
          * @param lkItemset
          * <p>Description:存储频繁项集 <p>
          * @date:          2016年3月9日下午9:50:33
          * @author         ysl
          */
         private static void insertLKitemset ( Map<List<String> , Integer> lkItemset ,String timeString)
         {
        	 Iterator<Entry<List<String>,Integer>> iterator = lkItemset.entrySet().iterator();
        	 
        	 Entry<List<String>,Integer> entry ;
        	 //List<String> result = new ArrayList<String>();
        	 while ( iterator.hasNext() )
        	 {
        		 entry = iterator.next() ;
        		 
        		 List<String> key = entry.getKey() ;
        		 Integer value = entry.getValue() ;
        		 
        		// System.out.println("the key : ");
        		 StringBuffer sb = new StringBuffer();
        		 for ( int i = 0 ; i < key.size() ; i++ )
        		 {
        			 //System.out.print(key.get(i));
        			// System.out.print(",");
        			 sb.append(key.get(i));
        			 sb.append(",");
        		 }
        		String  classify = sb.substring(0, sb.length()-1);
        		FileReader.insertData(classify,timeString,min_support );
        		System.out.println("频繁项为--："+classify);
        		//result.add(classify);
        		 //System.out.println("the value : "+ value.intValue());
        	 }
        	  
         }
         /**
          * 
          * @return:       List<String> 
          * @param result
          * @return
          * <p>Description:将相应的code值转换为名称 <p>
          * @date:          2016年4月18日下午10:23:51
          * @author         ysl
          */
         public  List<Prefer> codeToName(String searchDay,String minSupport)
         {
        	 FrequentItemDao fid = new FrequentItemDao();
        	 List<Prefer> resultList = new ArrayList<Prefer>();
        	 List<String> result = fid.getFrequentItemsOfDay(searchDay,minSupport);
        	 if(searchDay!=null)
        	 {
            	 LocationClassifyDao lcd = new LocationClassifyDao();
            	 if (result!=null)
            	 {
    				for (String string : result)
    				{
    					Prefer p = new Prefer();
    					System.out.println("频繁项为-#-："+string);
    					String[] values = string.split(",");
    					StringBuffer names = new StringBuffer();
    					for (String value : values)
    					{
    						LoctionClassify lc=  lcd.getLocationClassifysByvalue(value);
    						System.out.println("名称为：-#-："+lc.getName());
    						if(lc.getName()!=null)
    						{
    							names.append(lc.getName()).append(",");
    						}
    						
    					}
    					System.out.println(names.toString());
    					if(names.length()>1)
    					{
    						String name= names.substring(0, names.length()-1).toString();
    						p.setClassify(name);
    					}
    					resultList.add(p);
    					
    				}
    			}
        	 }
        	 return resultList;
        	 //return result;
         }
         /**
          * 
          * @return:       int 
          * @param searchDay
          * @return
          * <p>Description: 判断挖掘的数据是否已经存在过<p>
          * @date:          2016年4月20日上午9:28:24
          * @author         ysl
          */
     	public int isFrequentItemsExists(String searchDay,String minSupport)
     	{
 			FrequentItemDao fid = new FrequentItemDao();
 			if(searchDay!=null)
 			{
 				return fid.isFrequentItemsExists(searchDay,minSupport);
 			}
 			else
 			{
 				return 0;
 			}
     	}
         
        
        
}