package happiness.addvote

import happiness.getvotes.Vote

interface Votes {
    fun add(vote: UserVote)
    fun all(): List<Vote>
}