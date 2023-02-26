<template>
    <div class="middle">
        <main>
            <Index :posts="viewPosts" v-if="page === 'Index'"/>
            <Users :users="users" v-if="page === 'Users'"/>
            <User v-if="page === 'User'"/>
            <Marks :marks="marks" v-if="page === 'Marks'"/>
            <WriteMark v-if="page === 'WriteMark'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
        </main>
    </div>
</template>

<script>
import Index from "./index/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "@/components/page/Users";
import Marks from "@/components/page/Marks";
import User from "@/components/page/User";
import WriteMark from "@/components/page/WriteMark";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        Users,
        Marks,
        User,
        Register,
        Enter,
        Index,
        WriteMark,
    },
    props: ["posts", "users", "marks", "foundUser"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id);
        },
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>
