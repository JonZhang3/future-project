<script setup lang="ts">
import { ElMessageBox } from 'element-plus'
import { useI18n } from '@/hooks/web/useI18n'
import { useCache } from '@/hooks/web/useCache'
import { removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { useRouter } from 'vue-router'
import { useDesign } from '@/hooks/web/useDesign'
import { useTagsViewStore } from '@/store/modules/tagsView'
import avatarImg from '@/assets/imgs/avatar.gif'

const tagsViewStore = useTagsViewStore()

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('user-info')

const { t } = useI18n()

const { wsCache } = useCache()

const { push, replace } = useRouter()

const user = wsCache.get('user')

const avatar = user.user.avatar ? user.user.avatar : avatarImg

const userName = user.user.nickname ? user.user.nickname : 'Admin'

const loginOut = () => {
    ElMessageBox.confirm(t('common.loginOutMessage'), t('common.reminder'), {
        confirmButtonText: t('common.ok'),
        cancelButtonText: t('common.cancel'),
        type: 'warning'
    })
        .then(async () => {
            resetRouter() // 重置静态路由表
            wsCache.clear()
            removeToken()
            tagsViewStore.delAllViews()
            replace('/login')
        })
        .catch(() => {})
}
const toProfile = async () => {
    push('/userinfo/profile')
}
</script>

<template>
    <el-dropdown :class="prefixCls" trigger="click">
        <div class="flex items-center">
            <img :src="avatar" alt="" class="w-[calc(var(--logo-height)-25px)] rounded-[50%]" />
            <span class="<lg:hidden text-14px pl-[5px] text-[var(--top-header-text-color)]">
                {{ userName }}
            </span>
        </div>
        <template #dropdown>
            <el-dropdown-menu>
                <ElDropdownItem>
                    <Icon icon="ep:tools" />
                    <div @click="toProfile">{{ t('common.profile') }}</div>
                </ElDropdownItem>
                <el-dropdown-item>
                    <Icon icon="ep:setting" />
                    <div>{{ t('common.setting') }}</div>
                </el-dropdown-item>
                <el-dropdown-item divided>
                    <Icon icon="ep:switch-button" />
                    <div @click="loginOut">{{ t('common.loginOut') }}</div>
                </el-dropdown-item>
            </el-dropdown-menu>
        </template>
    </el-dropdown>
</template>
