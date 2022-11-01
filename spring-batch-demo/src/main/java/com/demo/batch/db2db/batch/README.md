

# 测试记录

total = 4457 ,   chunkSize = 10,  pageSize = 10,    ItemWriter = 单数据写（MyBatisBatchItemWriter）
任务执行时间      39s142ms、 39s974ms

total = 4457 ,   chunkSize = 10,  pageSize = 10,    ItemWriter = 批量（一次写10条）
任务执行时间      9s276ms、   10s663ms、  11s414ms

total = 4457 ,   chunkSize = 100,  pageSize = 10,    ItemWriter = 批量（一次写100条）
任务执行时间      4s396ms、    3s573ms、   3s435ms

total = 4457 ,   chunkSize = 500,  pageSize = 10,    ItemWriter = 批量（一次写500条）
任务执行时间      3s709ms、 3s722ms、   3s752ms


total = 4457 ,   chunkSize = 500,  pageSize = 100,    ItemWriter = 批量（一次写500条）
任务执行时间      623ms、631ms、599ms

# 报错
Cannot change the ExecutorType when there is an existing transaction
位置 --> org.mybatis.spring.SqlSessionUtils.sessionHolder