package com.sadig.correctsum.domain.usecase

class CheckAnswerUseCase {
    fun checkStatement(nums : Pair<Int, Int>, answer: Int) : Boolean {
        return nums.first + nums.second == answer
    }
}