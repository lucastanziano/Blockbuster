package lucastanziano.blockbuster.data;

public DiscoverQuery{
    
    
    private String certification = null;
    private Integer primary_release_year = null;
         private String primary_release_date_gte = null;
          private String primary_release_date_lte = null;
          private String sort_by = null;
         private String vote_count.gte = null;
         private String vote_average_gte = null;
          private String vote_average_lte = null;
          private List<String> with_genres  = null;
          
          private DiscoverQuery(){
              
          }
          
          public static DiscoverQuery newInstance(){
              return new DiscoverQuery();
          }
          
          public DiscoverQuery withCertification(String certification){
              this.certification = certification;
              return this;
          }
          
          public DiscoverQuery withPrimaryReleaseDate(Integer year){
              this.primary_release_year = year;
               withReleaseDateRange(null, null);
             return this;
          }
          
          public DiscoverQuery withReleaseDateRange(Integer fromYear, Integer toYear){
            return this;
          }
          
          public DiscoverQuery withVoteCountGreaterThen(int minVoteCount){
             return this;
          }
    
    
}