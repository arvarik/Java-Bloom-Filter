# Java-Bloom-Filter

### Overview
A Bloom Filter is a probabilistic data structure that quickly determines if a term is in a set or not.

A common example to illustrate the use case is determining if a username is taken or not. Suppose you have a website
where users can create an account and are distinguished by username. You may keep a list of usernames and check it
anytime a new person creates an account so that there are no clashes in usernames.

However, this could be very expensive computationally if your implementation does not make use of hashes,
but the bigger problem arises with the cost of space. Keeping a set of usernames can grow very fast and although
nowadays computers can handle sets of millions of items as small as a username, problems may arise when we reach
the billions/trillions territory or if we want to check containment of much larger objects.

At its core a bloom filter is a bitset accompanied by some hash functions which help drastically reduce the space needed
and in some cases the time as well.


### How It Works

The main operations of a bloomfilter are very simple. We'll go back to the username example to help illustrate.

Suppose we have a bloom filter with a bitset currently with 1000 spaces all set to 0. The bloom filter also
contains 3 hash functions which can convert any string into a number between 0 and 999 (I'll explain all the numbers
of a bloomfilter later in the post).

Anytime a user wants to create an account, the bloomfilter runs the username through the 3 hash functions
and out come 3 hashes which represent an index on the bitset. The bloomfilter will check these specific indices
and if any of the three indices point to a location in the bitset where it is not set (it is still 0) then we
know that the username is definitely not taken. The bloomfilter will in a way "add" the username by then setting
the same indices in the bitset to 1. Even if two of the indices are set but one isn't, we are still 100% sure that
the username is not in the set and the bloomfilter will go and set the index that was not set yet.


Now suppose a person wants to create an account with a username that is already taken. The bloomfilter again runs
the username through the 3 hash functions, and gets the corresponding indices. But now, the bloomfilter sees
that all the bits in the bitset with indices from the hashes are set to 1. This means that the username is 
*possibly* in the set. The bloomfilter cannot "add" the username by setting any bits since all the corresponding
indices have been set. In most ways of implementing bloom filters, this essentially means that the username is
*probably* in the set. There is no 100% guarantee to figure out if a username is definitely in the set, but we
can adjust the bitset size and the number of hash functions to get a false positive rate (bloomfilter says a
username is probably in the set when it is not actually in the set) small enough for our liking.


### Getting Optimal Bitset Size and Number of Hash Functions 