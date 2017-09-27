InMemory Queue is the solution for the below requirement - 

● Queue holds JSON messages
● Allow subscription of consumers to messages that match a particular
  expression
● Consumers register callbacks that will be invoked whenever there is a new
  message
● Queue will have one producer and multiple consumers
● Consumers might have dependency relationships between them. For ex, if
  there are three consumers A, B and C. One dependency relationship can be
  that C cannot consumer a particular message before A and B have consumed
  it. C -> (A,B) (-> means must process after)
● Queue is bounded in size and completely held in-memory. Size is configurable.
● Handle concurrent writes and reads consistently between producer and
  consumers.
● Provide retry mechanism to handle failures in message processing.

Testablity -
Run the launcher to launch the InMemoryQueue, which creates the InMemoryQueue that is configurable.
Steps  :1. Create Producer.
        2. Create Listener attach rule set.
        3. Write buisness Logic in Business Logic Interface.
        4. Register the consumers, and start the InMemoryQueue.
