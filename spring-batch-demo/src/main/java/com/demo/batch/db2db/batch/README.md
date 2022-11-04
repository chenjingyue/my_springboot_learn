

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


线程池
total = 100000（十万） ,   chunkSize = 100,  pageSize = 100,    ItemWriter = 批量（一次写100条）
任务执行时间      13s795ms


total = 100000（十万） ,   chunkSize = 500,  pageSize = 500,    ItemWriter = 批量（一次写500条）
3s20ms  2s688ms  2s522ms    2s978ms 2s581ms 2s501ms

# 报错
    Cannot change the ExecutorType when there is an existing transaction
位置 --> org.mybatis.spring.SqlSessionUtils.sessionHolder


    Step already complete or not restartable, so no action to execute
jobLauncher.run(job, jobParameters); 运行任务时添加一个不同的参数值
创建任务时，设置 .incrementer(new RunIdIncrementer())


# 多线程问题   MyBatisPagingItemReader
设置参数 .taskExecutor(TaskExecutor)        pageSize = 10   maxItemCount = 200
多线程下，reader、processor、writer 都是通过线程处理，存在实际读取数据量大于设置的最大数量
org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader.read()  currentItemCount字段非线程安全
多线程操作currentItemCount++ 操作，可能会出现赋值覆盖（a,b线程都更新为同一值），导致变量统计到的数据量小于真实读取数据量



# 事务提交
org.springframework.batch.core.step.tasklet.TaskletStep#doExecute