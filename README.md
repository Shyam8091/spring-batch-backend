# spring-batch-backend
In this Application I have used the spring batch with MYSQL database.
Exposed an endpoint which triggers the following task

# 1.FlatFileItemReader to read the file internally used the DefaultLineMapper to read lines
# 2.ItemProcessor to process the file(If any manipulation is require can be done while processing the file)
# 3.ItemWriter to write the file, I have persisted the data in MYSQL db

We can also pass the file in multipart format in requestbody and do the reading,processingand writting.
