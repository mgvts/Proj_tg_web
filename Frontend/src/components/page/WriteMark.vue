<template>
    <div class="form">
        <div class="header">Write Post</div>
        <div class="body">
            <form @submit.prevent="onWriteMark">
                <div class="field">
                    <div class="name">
                        <label for="value">value</label>
                    </div>
                    <div class="value">
                        <input id="value" name="value" v-model="value" type="number"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="comment">comment</label>
                    </div>
                    <div class="value">
                        <textarea id="comment" name="comment" v-model="comment"></textarea>
                    </div>
                </div>
                <div class="error">{{ error }}</div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>
</template>

<script>

export default {
    name: "WriteMark",
    data: function () {
        return {
            value: "",
            comment: "",
            error: ""
        }
    },
    methods: {
        onWriteMark: function () {
            this.error = "";
            this.$root.$emit("onWriteMark", this.value, this.comment);
        }
    },
    beforeMount() {
        this.$root.$on("onWriteMarkValidationError", error => this.error = error);
    }
}
</script>

<style scoped>

</style>