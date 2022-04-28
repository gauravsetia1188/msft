https://leetcode.com/discuss/general-discussion/1115078/an-approach-to-the-design-of-memory-management-system

Hello All,

In this post I am writing a general approach to designing a memory management system. I am not sharing any code for this and only proposing the overall idea on how to approach this.

Requirements:
Design alloc and free functions for memory management system. Alloc will take the input size and return the memory chunk while free would take the memory chunk and make it available for future use.

Design:
First thing is we need to ensure we do not allocate a bigger chunk of memory for lesser memory requirement. In order to do this, we can divide the available memory into chunks of sizes in powers of 2 and maintain a list of addresses of such sizes. Depending on the conversation with interviwer you can keep a max limit on the largest chunk of memory that you want to support.

[head of memory chunks of size 2] -> [node1] -> [node2]
[head of memory chunks of size 4] -> [node1] -> [node2]
[head of memory chunks of size 8] -> [node1] -> [node2]

If we are to allocate memory based on the size, we need a way to lookup the head of these lists. We can use a hashtable to store the length of memory chunk as key and the head of the lists as value. These should be available at the boot time of the system.

MemorySizeHashTable(memory_size, head_of_list)
Each list node should have start address of physical memory, length of data it can hold, flag representing whether the memory is allocated or usable.

Node memory_unit {
	long start_address;
	int length;
	boolean allocated;
}
Alloc API: memory_alloc(size)

Convert the size to smallest power of two greater than passed size
Lookup the hash table to find the head of the list that satisify the length requirement
Traverse the list and find the first usabale node by checking the boolean flag
In a hashtable [NodeAddressHashTable], store the start_address as key and list node pointer as value before returning. This is needed for freeing the memory. Also flip the boolean indicating this is now allocated
If none of the nodes of current length are available, lookup for the next highest length and repeat steps 2 - 4 till we find free node
NodeAddressHashTable(start_address, memory_unit) => Used in free API
Free API: memory_free(start_address, size)

Lookup the NodeAddressHashTable to find the list memory_unit against the start address
Flip the boolean flag of the memory unit node making it availble for future use
Few cases that should be covered

In alloc
handle multiple threads making alloc calls by taking lock in critical section [identify critical section]
In free
check if length passed is valid
check for duplicate free
Bonus points

What if Process-1 allocates and process-2 tries to free? => Can be handled by storing pid in memory_unit during alloc and checking that during free
Detecting memory corruption => Have some pattern in start_address of each Node and check it during alloc and free and panic if corruption is detected.
How to make alloc faster?
Should we do memory fragmentation? [if we allocate 16 bytes for 2 bytes requirement]
Summary of data structures used

Memory Unit -> Metadata storage for each physical memory chunk
Node memory_unit {
	long start_address;
	int length;
	int pid;
	boolean allocated;
}
MemorySizeHashTable(memory_size, head_of_list)

Stores the head of memory chunks of memory_size length
NodeAddressHashTable(start_address, memory_unit)

Stores the memory_unit list node against the start_address used in free API
