# DataStructuresAssesment01
HND assesment about Stack, Queues and Arrays. Provides file reading from a folder of files, analyses and stores its content through different data structures. It produces a log displaying all the information about the items and files.

Case Scenario:
You have been asked to design a computer system to help with the transport of material taken to the international space station. The current state of the data during mission is required at all stages of the mission. The material has the following data: cargoID, weight and description, and is stored in containers on earth called pods. These are flown to the international space station and unloaded and stored in three different areas depending on cargo type. Due to the nature of space flight and the design of the space station you will need to implement a Bays(list), pods(stack) and a processing corridor(queue) in order to record the progress at each stage.

The following mission critical rules must be implemented:
• Maximum cargo items per pod 9.
• Maximum weight of cargo in a pod 1600kg
• Pod1 to be filled with 9 items before Pod2
• Pod1 to be full before a launch can proceed.
• Pod2 may be empty.


Adrian Sanchez Rodriguez ec1939656@edinburghcollege.ac.uk
H16Y Assessment Out3 Queue and Stack
The data files are stored in “/data”
The program reads through all the files contained in the data folder and produces a log stored in “/logs”, showing all stages and the data that is being processed
